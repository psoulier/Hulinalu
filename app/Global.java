import play.*;

import models.LocationDB;
import models.Location;
import models.Beach;

// POST my office: 21.297579, -157.81648
// POST 318B: 21.31085, -157.85782


public class Global extends GlobalSettings {
  public void onStart(Application app) {

  // Conditions
  Location.Condition  parking = new Location.Condition(
      "Parking", 
      "Empty",
      "Full",
      "How full the parking facilities are.");

  Location.Condition  crowd = new Location.Condition(
      "Crowd", 
      "Empty",
      "Packed",
      "How many people are at this location.");

  Location.Condition waterClarity = new Location.Condition(
      "Water Clarity",
      "Murky",
      "Crytal",
      "How clear the water is for diving, snorkling, etc.");

  // Facilities
  Location.Facility parkingLot = new Location.Facility(
      "Parking Facilities",
      "No",
      "Yes",
      "The types of parking facilities available at this location.");

  Location.Facility lifeguard = new Location.Facility(
      "Lifeguard",
      "No",
      "Yes",
      "The beach is monitored by lifeguards.");

  Location.Facility restrooms = new Location.Facility(
      "Restrooms",
      "No",
      "Yes",
      "Restrooms are available at this beach.");

  Location.Facility dogs = new Location.Facility(
      "Dogs Allowed",
      "No",
      "Yes",
      "Some beaches allow dogs. Be sure to follow park rules and leash laws.");
                                                     

  Location alaMoana = new Beach("Ala Moana Beach Park",
                                "Beach on the south shore of Oahu. The beach is protected by a reef so the water is usually calm.",
                                21.289984f, 
                                -157.847603f);

  alaMoana.addCondition( new Location.Condition(parking) );
  alaMoana.addCondition( new Location.Condition(crowd) );
  alaMoana.addCondition( new Location.Condition(waterClarity) );
  alaMoana.addFacility( new Location.Facility(parkingLot) );
  alaMoana.addFacility( new Location.Facility(lifeguard) );
  alaMoana.addFacility( new Location.Facility(dogs) );
  alaMoana.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(alaMoana);



  Location waikiki = new Beach("Waikiki Beach",
                               "Popular beach on the south shore of Oahu in the Waikiki area.",
                               21.276492f, -157.827809f);

  waikiki.addCondition( new Location.Condition(parking) );
  waikiki.addCondition( new Location.Condition(crowd) );
  waikiki.addCondition( new Location.Condition(waterClarity) );
  waikiki.addFacility( new Location.Facility(parkingLot) );
  waikiki.addFacility( new Location.Facility(lifeguard) );
  waikiki.addFacility( new Location.Facility(dogs) );
  waikiki.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(waikiki);
  }
}
