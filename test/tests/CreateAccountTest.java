package tests;

import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;
import tests.pages.IndexPage;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;
import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.google.common.collect.ImmutableMap;

import play.mvc.Result;

import com.avaje.ebean.Expr;

import models.LocationDB;
import models.Location;
import models.Account;
import models.UserUpdate;
import models.Tag;
import models.Feature;

import java.util.List;

public class CreateAccountTest {

  /**
   * Tests the controller and form portion of account creation.
   */
  @Test
  public void testCreateAccount() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        final String FIRST = "Bazooka";
        final String LAST = "Joe";
        final String EMAIL = FIRST + "." + LAST + "@rockets.com";
        final String PASSWORD = "Boom!";
        final String CONFIRM = "Boom!";

        Account acct;
        
        acct = new Account("Bazooka", "Joe", "bazooka.joe@rockets.com", "", "1234");
        LocationDB.addAccount(acct);

        Result  result = callAction(
          controllers.routes.ref.Application.createAccount(),
          fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
              "firstName", FIRST,
              "lastName", LAST,
              "email", EMAIL,
              "password", PASSWORD,
              "confirmPassword", CONFIRM))
          );

        acct = Account.authenticate(EMAIL, PASSWORD);
        assertThat(acct).isNotEqualTo(null);
        assertThat(acct.getFirstName()).isEqualTo(FIRST);

        assertThat(status(result)).isEqualTo(303);
        assertThat(session(result).get("userid")).isEqualTo( Long.toString(acct.getId()) );
      }
    });
  }

}




