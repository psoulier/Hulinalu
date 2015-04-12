import play.*;

import models.LocationDB;
import models.Location;
import models.Beach;

// POST my office: 21.297579, -157.81648
// POST 318B: 21.31085, -157.85782


public class Global extends GlobalSettings {
  public void onStart(Application app) {
    Location  beach;

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

  /*
  Location.Facility parkingLot = new Location.Facility(
      "Beach",
      "Rocky",
      "Sandy",
      "Composition of the shore (nice sand or rocks).");
  */
  
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

  beach = new Beach("Waimea Bay Beach Park",
                    "Located on the North shore of Oahu, Waimea bay is known for big wave surfing in the winter and calm water in the summer.",
                    21.643160f, -158.066383f);

  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);

  beach = new Beach("Kahe Point Beach",
                    "Kahe Point Beach is a great place for snorkling and shore dives.",
                    21.353441f, -158.130324f);

  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);

  beach = new Beach("Kailua Beach Park",
                    "Kailua Beach Park has nice white sand.",
                    21.397877f, -157.727080f);

  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);

  beach = new Beach("Sandy Beach Park",
                    "Nice beach with some crazy shore breaks when the surf is up.",
                    21.285874f, -157.673212f);
 
  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);

  beach = new Beach("Hanauma Bay",
                    "Hanauma Bay is a nature preserve that feature contains a variety of sea life.",
                    21.285874f, -157.693807f);
 
  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);


  beach = new Beach("Bellows Field Beach Park",
                    "Great beach on the windward side of Oahu.",
                    21.357712f, -157.709051f);

  beach.addCondition( new Location.Condition(parking) );
  beach.addCondition( new Location.Condition(crowd) );
  beach.addCondition( new Location.Condition(waterClarity) );
  beach.addFacility( new Location.Facility(parkingLot) );
  beach.addFacility( new Location.Facility(lifeguard) );
  beach.addFacility( new Location.Facility(dogs) );
  beach.addFacility( new Location.Facility(restrooms) );
  LocationDB.addLocation(beach);


  }

  
}
