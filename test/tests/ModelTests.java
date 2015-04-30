package tests;

import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;
import tests.pages.IndexPage;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;

import com.avaje.ebean.Expr;

import models.LocationDB;
import models.Location;
import models.Account;
import models.UserUpdate;
import models.Tag;
import models.Feature;

import java.util.List;

public class ModelTests {

  private void dbSetup() {
    Location  beach;
    Account   user1,
              user2,
              user3;

    // Make sure entities are empty (should be, but check anyway).
    assertThat(Account.find().all().size()).isEqualTo(0);
    assertThat(Location.find().all().size()).isEqualTo(0);

    user1 = new Account("Paul", "Shoe", "hulinalu@hulinalu.com", "", "1234");
    LocationDB.addAccount(user1);
    user2 = new Account("Joe", "Blow", "joe.blow@nowhere.net", "", "1234");
    LocationDB.addAccount(user2);
    user3 = new Account("Bazooka", "Joe", "bazooka.joe@nowhere.net", "", "1234");
    LocationDB.addAccount(user3);


    assertThat(Account.find().all().size()).isEqualTo(3);



    beach = new Location(
      user1,
      "Waimea Bay Beach Park",
      "Located on the North shore of Oahu, Waimea bay is known for big wave"
        +"surfing in the winter and calm water in the summer.",
      21.643160f, 
      -158.066383f);
    LocationDB.addLocation(beach);

    beach = new Location(
      user1,
      "Waikiki Beach", 
      "Popular beach on the sourth shore of Oahu", 
      21.276492f, 
      -157.827809f);
    LocationDB.addLocation(beach);

    beach = new Location(
      user1,
      "Ala Moana Beach Park",
      "Beach on the south shore with a protective man-made reef.",
      21.289984f,
      -157.847603f);
    LocationDB.addLocation(beach);

    beach = new Location(
      user2,
      "Kahe Point Beach",
      "Kahe Point Beach is a great place for shore dives and snorkeling.",
      21.353441f, 
      -158.130324f);
    LocationDB.addLocation(beach);

    beach = new Location(
      user2,
      "Kailua Beach Park",
      "Kailua Beach Park has nice white sand.",
      21.397877f, 
      -157.727080f);
    LocationDB.addLocation(beach);

    beach = new Location(
      user3,
      "Sandy Beach Park",
      "Nice beach with some crazy shore breaks when the surf is up.",
      21.285874f,
      -157.673212f);
    LocationDB.addLocation(beach);

    assertThat(Location.find().all().size()).isEqualTo(6);    
  }

  @Test
  public void testCreate() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {

        dbSetup();

        // Should be able to find a single user in the system based on email.
        List<Account> users = Account.find().where().eq("email", "hulinalu@hulinalu.com").findList();
        assertThat(users.size()).isEqualTo(1);

        // This user created 3 locations. Make sure we can find those three beaches.
        List<Location> locs = Location.find().where().eq("creator.id", users.get(0).getId()).findList();
        assertThat(locs.size()).isEqualTo(3);
        assertThat(locs.get(0).getCreator().getId()).isEqualTo(users.get(0).getId());
      }
    });
  }


  @Test
  public void testTagUpdate() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        dbSetup();

        List<Tag> tags = Tag.find().all();
        assertThat(tags.size()).isGreaterThan(0);

        // Pick a random tag.
        Tag tag = tags.get(2);
        
        Account user;
        user = Account.find().where().eq("email", "hulinalu@hulinalu.com").findUnique();
        assertThat(user).isNotEqualTo(null);

        // There shouldn't be any user updates at this point.
        assertThat(UserUpdate.find().all().size()).isEqualTo(0);
        
        UserUpdate  uu;
        uu = UserUpdate.find().where().and( Expr.eq("account.id", user.getId()), Expr.and(Expr.eq("type", UserUpdate.TAG), Expr.eq("parentId", tag.getId()) ) ).findUnique();
        assertThat(uu).isEqualTo(null);

        tag.update(user, tag.YES);
        tag.update(user, tag.YES);

        // A user is only allowed to vote a single time. Make sure "yes" is
        // equal to 1 after multiple updates.
        assertThat(tag.getYes()).isEqualTo(1);

        // Change to no. This should undo the yes and add one to the no.
        tag.update(user, tag.NO);
        assertThat(tag.getYes()).isEqualTo(0);
        assertThat(tag.getNo()).isEqualTo(1);

        uu = UserUpdate.find().where().and( Expr.eq("account.id", user.getId()), Expr.and(Expr.eq("type", UserUpdate.TAG), Expr.eq("parentId", tag.getId()) ) ).findUnique();
        assertThat(uu).isNotEqualTo(null);

        assertThat(UserUpdate.find().all().size()).isEqualTo(1);
      }
    });
  }


  /**
   * Test updates to a tag from multiple users.
   */
  @Test
  public void testMultipleTagUpdate() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        dbSetup();

        // - We should have a tag ID
        // - From the tag ID, we can get the Tag object associated to the widget
        // - Need both the tag and user update to send back to view (display "dots")
        // - Find Tag based on ID
        // - From tag, find UserUpdate via User.id (should have current user as part of session/login info).
        //
        List<Tag> tags = Tag.find().all();
        assertThat(tags.size()).isGreaterThan(0);

        // Pick a random tag.
        Tag tag = tags.get(2);
        
        Account user1,
                user2;
        user1 = Account.find().where().eq("email", "hulinalu@hulinalu.com").findUnique();
        user2 = Account.find().where().eq("email", "bazooka.joe@nowhere.net").findUnique();
        assertThat(user1).isNotEqualTo(null);
        assertThat(user2).isNotEqualTo(null);

        // There shouldn't be any user updates at this point.
        assertThat(UserUpdate.find().all().size()).isEqualTo(0);
        

        tag.update(user1, tag.YES);
        tag.update(user2, tag.YES);

        // A user is only allowed to vote a single time. Make sure "yes" is
        // equal to 1 after multiple updates.
        assertThat(tag.getYes()).isEqualTo(2);
        assertThat(tag.getNo()).isEqualTo(0);

        tag.update(user2, tag.NO);
        assertThat(tag.getYes()).isEqualTo(1);
        assertThat(tag.getNo()).isEqualTo(1);


        UserUpdate uu = UserUpdate.find().where().and( Expr.eq("account.id", user1.getId()), Expr.and(Expr.eq("type", UserUpdate.TAG), Expr.eq("parentId", tag.getId()) ) ).findUnique();
        assertThat(uu).isNotEqualTo(null);
        uu = UserUpdate.find().where().and( Expr.eq("account.id", user2.getId()), Expr.and(Expr.eq("type", UserUpdate.TAG), Expr.eq("parentId", tag.getId()) ) ).findUnique();
        assertThat(uu).isNotEqualTo(null);
        assertThat(UserUpdate.find().all().size()).isEqualTo(2);
      }
    });
  }

  /**
   * Tests Feature updates from multiple users.
   */
  @Test
  public void testMultipleFeatureUpdate() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
      public void run() {
        dbSetup();

        List<Feature> features = Feature.find().all();
        assertThat(features.size()).isGreaterThan(0);

        // Pick a random feature.
        Feature feature = features.get(2);
        
        Account user1,
                user2;
        user1 = Account.find().where().eq("email", "hulinalu@hulinalu.com").findUnique();
        user2 = Account.find().where().eq("email", "bazooka.joe@nowhere.net").findUnique();
        assertThat(user1).isNotEqualTo(null);
        assertThat(user2).isNotEqualTo(null);

        // There shouldn't be any user updates at this point.
        assertThat(UserUpdate.find().all().size()).isEqualTo(0);
        

        feature.update(user1, 1);
        feature.update(user2, 1);

        // A user is only allowed to vote a single time. Make sure "yes" is
        // equal to 1 after multiple updates.
        assertThat(feature.getScore()).isEqualTo(1);

        feature.update(user2, 5);
        assertThat(feature.getScore()).isEqualTo(3);

        // After the updates, there should be a UserUpdate for each of the two users.
        UserUpdate uu = UserUpdate.find().where().and( Expr.eq("account.id", user1.getId()), 
            Expr.and(Expr.eq("type", UserUpdate.FEATURE), Expr.eq("parentId", feature.getId()) ) 
            ).findUnique();
        assertThat(uu).isNotEqualTo(null);

        uu = UserUpdate.find().where().and( Expr.eq("account.id", user2.getId()), 
            Expr.and(Expr.eq("type", UserUpdate.FEATURE), Expr.eq("parentId", feature.getId()) ) 
            ).findUnique();
        assertThat(uu).isNotEqualTo(null);

        // Since only two distinct users did updates, there should only be 2 
        // UserUpdate enities in the DB.
        assertThat(UserUpdate.find().all().size()).isEqualTo(2);
      }
    });
  } 

}

