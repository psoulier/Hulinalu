package models;

import java.util.ArrayList;

public class LocationDB {
  private static ArrayList<Location>      locations = new ArrayList<Location>();

  public static Location getLocation(float lat, float lng) {
    System.out.format("lat=%f lng=%f %n", lat, lng);
    
    return locations.get(0);
  }

  public static Boolean isKnownLocation(float lat, float lng) {
    for (Location loc : locations) {
      loc.isWithin(lat, lng, 15.0f);
    }

    return true;
  }

  public static void addLocation(Location loc) {
    locations.add(loc);
  }
}
