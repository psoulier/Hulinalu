import models.Location;
import models.LocationDB;
import models.Account;
import play.Application;
import play.GlobalSettings;
import play.*;

// POST my office: 21.297579, -157.81648
// POST 318B: 21.31085, -157.85782


/**
 * Populates the fake database.
 * <p>
 * This class is just a bunch of static data generation. Nothing really that
 * interesting here.
 */
public class Global extends GlobalSettings {

  /**
   * Populates data on startup.
   *
   * @param app The application object.
   */
  public void onStart(Application app) {

    if (Play.application().configuration().getString("myTesting").equals("false")) {
      Location  beach;
      Account   user;

      user = Account.find().where().eq("email", "joebob@jb.com").findUnique();

      beach = Location.find().where().eq("name", "Waimea Bay Beach Park").findUnique();
      if (beach == null) {
        beach = new Location(
          user,
          "Waimea Bay Beach Park",
          "Located on the North shore of Oahu, Waimea bay is known for big wave"
            +"surfing in the winter and calm water in the summer.",
          21.643160f, 
          -158.066383f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Waikiki Beach").findUnique();
      if (beach == null) {
        beach = new Location(
          user,
          "Waikiki Beach", 
          "Popular beach on the sourth shore of Oahu", 
          21.276492f, 
          -157.827809f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Ala Moana Beach Park").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Ala Moana Beach Park",
            "Beach on the south shore of Oahu. The beach is protected by a reef so the water is usually calm.",
            21.289984f,
            -157.847603f);
        LocationDB.addLocation(beach);
      }


      beach = Location.find().where().eq("name", "Kahe Point Beach").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Kahe Point Beach",
            "Kahe Point Beach is a great place for snorkling and shore dives.",
            21.353441f, -158.130324f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Kailua Beach Park").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Kailua Beach Park",
            "Kailua Beach Park has nice white sand.",
            21.397877f, -157.727080f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Sandy Beach Park").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Sandy Beach Park",
            "Nice beach with some crazy shore breaks when the surf is up.",
            21.285874f, -157.673212f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Hanauma Bay").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Hanauma Bay",
            "Hanauma Bay is a nature preserve that feature contains a variety of sea life.",
            21.269475f, -157.693791f);
        LocationDB.addLocation(beach);
      }


      beach = Location.find().where().eq("name", "Bellows Field Beach Park").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Bellows Field Beach Park",
            "Great beach on the windward side of Oahu.",
            21.357712f, -157.709051f);
        LocationDB.addLocation(beach);
      }

      beach = Location.find().where().eq("name", "Sharks Cove").findUnique();
      if (beach == null) {
        beach = new Location(
            user,
            "Sharks Cove",
            "Located on the North shore, Shark's cove is a spot known for good snorkeling and shore dives.",
            21.651281f, -158.062227f);
        LocationDB.addLocation(beach);
      }
    }

  }
}
