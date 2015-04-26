package tests;

import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;
import tests.pages.IndexPage;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;

import models.LocationDB;
import models.Location;
import models.User;

public class ModelTests {

  @Test
  public void testCreate() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        Location  beach;
        User      user;

        assertThat(Location.find().all().size()).isEqualTo(0);

        beach = new Location(
          "Waikiki Beach", 
          "Popular beach on the sourth shore of Oahu", 
          21.276492f, 
          -157.827809f);
        LocationDB.addLocation(beach);

        beach = new Location(
          "Ala Moana Beach Park",
          "Beach on the south shore with a protective man-made reef.",
          21.289984f,
          -157.847603f);
        LocationDB.addLocation(beach);

        assertThat(Location.find().all().size()).isEqualTo(2);

        assertThat(User.find().all().size()).isEqualTo(0);
        user = new User("joebob@nowhere.net", "", "1234");
        LocationDB.addUser(user);
        assertThat(User.find().all().size()).isEqualTo(1);

        LocationDB.addLocationByUser(beach, user);



      }
    });
  }
}

