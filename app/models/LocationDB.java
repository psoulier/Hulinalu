package models;

import java.util.ArrayList;
import java.util.List;

public class LocationDB {
  public static final float               MAX_DISTANCE = (0.00001f / 1.1132f) * 15.0f;
  private static ArrayList<Location>      locations = new ArrayList<Location>();



  public static Location getClosestLocation(float lat, float lng) {
    Location  closestLoc = null;
    float     closestDist = 100000.0f;   // No such distance on Earth (literally).

    for (Location loc : locations) {
      float d = loc.getDistanceFrom(lat, lng);

      System.out.format("name: %s dist=%f %n", loc.getName(), d);

      if (d < closestDist) {
        closestLoc = loc;
        closestDist = d;
      }
    }

    return closestLoc;
  }

  public static Boolean isUserInProximity(float lat, float lng) {
    return getClosestLocation(lat, lng).getDistanceFrom(lat, lng) <= MAX_DISTANCE;
  }
  
  public static List<Location> getLocations() {
    return locations;
  }

  public static Location getLocation(String locQuery) {
    Location  locResult = null;

    for (Location loc : locations) {
      if ( loc.getName().equals(locQuery) ) {
        locResult = loc;
        break ;
      }
    }

    // todo: handle null pointer.

    return locResult;
  }

  public static void addLocation(Location loc) {
    locations.add(loc);
  }
}
