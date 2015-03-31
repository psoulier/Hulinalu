package models;

import java.util.ArrayList;

public class LocationDB {
  private static ArrayList<Location>      locations = new ArrayList<Location>();

  public static Location getLocation(float lat, float lng) {
    return null;
  }

  public static Boolean isAtLocation(float lat, float lng) {
    return true;
  }

  public static void addLocation(Location loc) {
    locations.add(loc);
  }
}
