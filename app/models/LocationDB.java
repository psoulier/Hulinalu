package models;

import java.util.ArrayList;
import java.util.List;

/**
 * A fake DB for all the locations in the system.
 */
public class LocationDB {
  public static final float               MAX_DISTANCE = (0.00001f / 1.1132f) * 15.0f;
  private static ArrayList<Location>      locations = new ArrayList<Location>();


  /**
   * Finds the location in the DB that is closest to the coordinates provided.
   * @param lat Latitude of position to check.
   * @param lat Longitude of position to check.
   */
  public static Location getClosestLocation(float lat, float lng) {
    Location  closestLoc = null;
    float     closestDist = 100000.0f;   // No such distance on Earth (literally).

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
   *
   * This function determines if the location closest to the coordinates 
   * specified is within a predefined distance to be considered "at" a
   * location.
   * @param lat Latitude.
   * @param lng Longitude.
   * @return True if the coordinates are close enough, false otherwise.
   */
  public static Boolean isUserInProximity(float lat, float lng) {
    return getClosestLocation(lat, lng).getDistanceFrom(lat, lng) <= MAX_DISTANCE;
  }
  
  /**
   * Returns a list of all locations in DB.
   * @return List of locations.
   */
  public static List<Location> getLocations() {
    return locations;
  }

  /**
   * Finds a location from a query string.
   *
   * This implements the search functionality.  Currently, it's just a literal
   * string match.
   * @param Query data.
   * @return Returns the first location matching the search or null if none match.
   */
  public static Location getLocation(String locQuery) {
    Location  locResult = null;

    for (Location loc : locations) {
      if ( loc.getName().equals(locQuery) ) {
        locResult = loc;
        break ;
      }
    }

    // TODO: handle null pointer.

    return locResult;
  }

  /**
   * Returns a location by unique ID.
   * @param ID to look for.
   * @return Returns location object associated to ID.
   */
  public static Location getLocationById(String id) {
    Location  locResult = null;

    for (Location loc : locations) {
      if ( loc.getId().equals(id) ) {
        locResult = loc;
        break ;
      }
    }

    // TODO: handle null pointer.

    return locResult;
  }

  /**
   * Returns a list of locations that match the search query.
   * @param Search query string.
   * @return A list of locations match query.
   */
  public static List<Location> searchLocations(String searchQuery) {
    ArrayList<Location> results = new ArrayList<Location>();

    // Build up a list of all locations matching the query.
    for (Location loc : locations) {
      String name = new String( loc.getName().toLowerCase() );

      searchQuery = searchQuery.toLowerCase();
      if ( name.indexOf(searchQuery) >= 0 ) {
        results.add(loc);
      }
    }

    return results;
  }

  /**
   * Adds a location to the DB.
   * @param loc Location to add.
   */
  public static void addLocation(Location loc) {
    locations.add(loc);
  }
}
