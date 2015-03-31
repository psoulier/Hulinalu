import play.*;

import models.LocationDB;
import models.Location;
import models.Trail;

public class Global extends GlobalSettings {
  public void onStart(Application app) {
    Location.Feature  feature;

    System.out.format("Here%n");
    Location  manoaFalls = new Trail("Manoa Falls", 
                                     "A nice hike through Manoa with a waterfall at the end",
                                     21.31085f, 
                                     -157.85782f);

    System.out.format("Here%n");
    feature = new Location.Feature("Parking", "How full is the parking lot?", new String[] {"Plenty", "Some", "Full"});
    System.out.format("Thre%n");
    manoaFalls.addCondition(feature);

    System.out.format("Here%n");
    feature = new Location.Feature("Crowd", "How many people are there?", new String[] {"None", "A few", "Busy", "Crowded"});
    manoaFalls.addCondition(feature);
    
    System.out.format("Here%n");
    LocationDB.addLocation(manoaFalls);
  }
}
