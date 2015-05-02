package models;

import java.util.ArrayList;
import java.util.List;

/**
 * A fake DB for all the locations in the system.
 */
public class LocationDB {
  /**
   * Ok.
   */
  private static final float METER = 0.00001f;

  /**
   * Ok.
   */
  private static final float DD_METER = 1.1132f;

  /**
   * Ok.
   */
  private static final float METER_15 = 15.0f;

  /**
   * Big distance.
   */
  private static final float FAR_AWAY = 100000.0f;


  /**
   * Max distance to be considered "at" a place.
   */
  public static final float MAX_DISTANCE = (METER / DD_METER) * METER_15;
  private static ArrayList<Location> locations = new ArrayList<Location>();


  /**
   * Finds the location in the DB that is closest to the coordinates provided.
   *
   * @param lat Latitude of position to check.
   * @param lng Longitude of position to check.
   * @return Returns location to specified coordinates.
   */
  public static Location getClosestLocation(float lat, float lng) {
    List<Location>  locations;
    Location        closestLoc = null;
    float           closestDist = FAR_AWAY;   // No such distance on Earth (literally).

    locations = Location.find().all();

    // Check the given coordinates agains all locations.
    for (Location loc : locations) {
      float d = loc.getDistanceFrom(lat, lng);

      // Check if the current location is closer.
      if (d < closestDist) {
        closestLoc = loc;
        closestDist = d;
      }
    }

    return closestLoc;
  }

  /**
   * Implements an "are you here" function.
   * <p>
   * This function determines if the location closest to the coordinates
   * specified is within a predefined distance to be considered "at" a
   * location.
   *
   * @param lat Latitude.
   * @param lng Longitude.
   * @return True if the coordinates are close enough, false otherwise.
   */
  public static Boolean isUserInProximity(float lat, float lng) {
    return getClosestLocation(lat, lng).getDistanceFrom(lat, lng) <= MAX_DISTANCE;
  }

  /**
   * Returns a list of all locations in DB.
   *
   * @return List of locations.
   */
  public static List<Location> getLocations() {
    return locations;
  }

  /**
   * Finds a location from a query string.
   * <p>
   * This implements the search functionality.  Currently, it's just a literal
   * string match.
   *
   * @param locQuery data.
   * @return Returns the first location matching the search or null if none match.
   */
  public static Location getLocation(String locQuery) {
    Location locResult = null;

    for (Location loc : locations) {
      if (loc.getName().equals(locQuery)) {
        locResult = loc;
        break;
      }
    }

    return locResult;
  }

  /**
   * Returns a location by unique ID.
   *
   * @param id to look for.
   * @return Returns location object associated to ID.
   */
  public static Location getLocationById(String id) {
    Location locResult = null;

    // TODO - all broken
    /*
    for (Location loc : locations) {
      if (loc.getId().equals(id)) {
        locResult = loc;
        break;
      }
    }
    */

    return locResult;
  }

  /**
   * Returns a list of locations that match the search query.
   *
   * @param searchQuery query string.
   * @return A list of locations match query.
   */
  public static List<Location> searchLocations(String searchQuery) {
    return Location.find().where().ilike("name", "%" + searchQuery + "%").findList();
  }

  /**
   * Adds a location to the DB.
   *
   * @param loc Location to add.
   */
  public static void addLocation(Location location) {
    location.save();
  }

  public static void addAccount(Account account) {
    account.save();
  }
}
