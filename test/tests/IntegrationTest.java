package tests;

import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;
import tests.pages.IndexPage;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.domain.FluentList;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Runs a server with a fake in-memory database to test the system.
 */
public class IntegrationTest {

  private static final int    PORT = 3333;

  /**
   * Check to see that the index page get be retrieved.
   */
  @Test
  public void testRetrieveIndexPage() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage indexPage = new IndexPage(browser.getDriver(), PORT);
        browser.goTo(indexPage);
        indexPage.isAt();
      }
    });
  }

  /**
   * Test a simple search that should produce a results page. 
   *
   * Enters a search string, follows the link to the results page, and then to
   * the location page.
   */
  @Test
  public void testSearchHit() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage page = new IndexPage(browser.getDriver(), PORT);
        String    url="";

        // Start off at the index page.
        browser.goTo(page);
        page.search("Waikiki");

        /* This is ugly...can't find a more elegant way to do this.  Get the
         * entire page back as text and look for the href tag that I know
         * Waikiki beach should be. Extract the URL from that.
         */
        String[] hrefStrings = page.pageSource().split("\n"); 
        for (String s : hrefStrings) { 
          if (s.indexOf("<a href=\"/locationPage?locQuery=Waikiki+Beach\">") != -1) { 
            url = s.substring( s.indexOf("/"), s.lastIndexOf("\"") );
          } 
        }

        // Make sure the URL was found.
        assertThat(url).isNotEqualTo("");

        // Go to the location page that should have Waikiki beach.
        page.setUrl("http://localhost:" + PORT + url);
        browser.goTo(page);

        /* Make sure the page contains "Conditions" since it's specific to the location
         * page and "Waikiki" to make sure it's the correct location page.
         */
        assertThat(page.pageSource()).contains("Conditions");
        assertThat(page.pageSource()).contains("Waikiki");
     }
    });
  }

  /**
   * Test a simple search that should not find any results.
   *
   * Enters an invalid search string, follows the link to the results page, and
   * then makes sure no results were found.
   */
  @Test
  public void testSearchMiss() {
    running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        browser.maximizeWindow();
        IndexPage page = new IndexPage(browser.getDriver(), PORT);

        // Start off at the index page.
        browser.goTo(page);

        // This is hopefully a search that doesn't return any results.
        page.search("loeijrnvm&**^#");

        assertThat(page.pageSource()).contains("No results found");
     }
    });
  }

}
