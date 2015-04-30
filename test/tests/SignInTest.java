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

public class SignInTest {

  @Test
  public void testAuthenticateSuccess() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {

        Account acct;
        
        acct = new Account("Bazooka", "Joe", "bazooka.joe@rockets.com", "", "1234");
        LocationDB.addAccount(acct);

        Result  result = callAction(
          controllers.routes.ref.Application.authenticate(),
          fakeRequest().withFormUrlEncodedBody(ImmutableMap.of("username", "bazooka.joe@rockets.com",
              "password", "1234"))
          );

        assertThat(status(result)).isEqualTo(303);
        assertThat(session(result).get("userid")).isEqualTo( Long.toString(acct.getId()) );
      }
    });
  }

  @Test
  public void testAuthenticateBadPassword() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {

        Account acct;
        
        acct = new Account("Bazooka", "Joe", "bazooka.joe@rockets.com", "", "1234");
        LocationDB.addAccount(acct);

        Result  result = callAction(
          controllers.routes.ref.Application.authenticate(),
          fakeRequest().withFormUrlEncodedBody(ImmutableMap.of("username", "bazooka.joe@rockets.com",
              "password", "12345"))
          );

        assertThat(status(result)).isEqualTo(400);
        assertThat(session(result).get("userid")).isEqualTo(null);
      }
    });
  }

  @Test
  public void testAuthenticateBadUsername() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        Account acct;

        acct = new Account("Bazooka", "Joe", "bazooka.joe@rockets.com", "", "1234");
        LocationDB.addAccount(acct);

        Result  result = callAction(
          controllers.routes.ref.Application.authenticate(),
          fakeRequest().withFormUrlEncodedBody(ImmutableMap.of("username", "bazooka.bob@rockets.com",
              "password", "1234"))
          );

        assertThat(status(result)).isEqualTo(400);
        assertThat(session(result).get("userid")).isEqualTo(null);
      }
    });
  }



  
}

