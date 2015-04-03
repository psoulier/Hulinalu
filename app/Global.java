import play.*;

import models.LocationDB;
import models.Location;
import models.Trail;
import models.Building;
// POST my office: 21.297579, -157.81648
// POST 318B: 21.31085, -157.85782


public class Global extends GlobalSettings {
  public void onStart(Application app) {
    Location.Feature  feature;

    Location  manoaFalls = new Building("Capitol Place", 
                                        "Highrise condo in downtown Honolulu.",
                                        21.31085f, 
                                        -157.85782f);

    feature = new Location.Feature("Parking", "How full is the parking lot?", new String[] {"Plenty", "Some", "Full"});
    manoaFalls.addCondition(feature);

    feature = new Location.Feature("Crowd", "How many people are there?", new String[] {"None", "A few", "Busy", "Crowded"});
    manoaFalls.addCondition(feature);
    
    LocationDB.addLocation(manoaFalls);



    Location  post318B = new Building("POST 318B",
                                      "Classroom in the POST building.",
                                      21.31085f,
                                      -157.81648f);
    LocationDB.addLocation(post318B);



    Location  paulsOffice = new Building("Paul's Office",
                                         "Office 303-E in POST building.",
                                         21.297579f,
                                         -157.81648f);
    LocationDB.addLocation(paulsOffice);

  }
}