package tests.pages;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

/**
 * Provides testing support for the Index page.
 * @author Philip Johnson
 */
public class IndexPage extends FluentPage {
  private String url;
  
  /**
   * Create the IndexPage.
   * @param webDriver The driver.
   * @param port The port.
   */
  public IndexPage(WebDriver webDriver, int port) {
    super(webDriver);
    this.url = "http://localhost:" + port;
  }
  
  /**
   * Gets the pages URL.
   *
   * @return Returns URL.
   */
  @Override
  public String getUrl() {
    return this.url;
  }

  /**
   * Sets the URL.
   *
   * @param url New URL for the page.
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Checks if page title is correct.
   */
  @Override
  public void isAt() {
    assertThat(title()).isEqualTo("Hulinalu");
  }

  /**
   * Performs a search for a given string.
   *
   * @param searchQuery String to search for.
   * @return Returns the results page created by the query data.
   */
  public void search(String queryData) {
    fill("#search-input").with(queryData);

    submit("#submit");
  }

  /**
   * Checks if contact info exists anywhere on the page.
   *
   * @param firstName First name
   * @param lastName Last name
   * @param telephone Telephone #
  public void hasContactInfo(String firstName, String lastName, String telephone) {
    assertThat(pageSource()).contains(firstName);
    assertThat(pageSource()).contains(lastName);
    assertThat(pageSource()).contains(telephone);
  }
   */
  
}
