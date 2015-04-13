package models;

import java.util.ArrayList;
import java.util.List;


/**
 * Defines a location.
 * <p>
 * A location contains all the static information necessary to describe a
 * location.
 */
public class Location {
  /**
   * Constant ID for beaches.
   */
  public static final int LOC_BEACH = 1;

  /**
   * Used to create unique IDs.
   */
  public static int nextId = 1;


  /**
   * Subclass that defines a Condition feature (crowd size, full/empty parking, etc.).
   */
  public static class Condition extends Feature {

    /**
     * Constructs a new condition.
     *
     * @param name      Name of condition.
     * @param lowLabel  Label for low end of scale.
     * @param highLabel Label for high end of scale.
     * @param info      Information about the condition.
     */
    public Condition(String name, String lowLabel, String highLabel, String info) {
      super(name, lowLabel, highLabel, info);
    }

    /**
     * Condition copy consuctor.
     *
     * @param cond Object to copy.
     */
    public Condition(Condition cond) {
      super(cond);
    }
  }

  /**
   * Facility object that represents a more static feature (dogs allowed, life guarded, etc.).
   */
  public static class Facility extends Feature {

    /**
     * Constructs new Facility.
     *
     * @param name      Name of facility.
     * @param lowLabel  Label for the low end of scale.
     * @param highLabel Label for the high end of scale.
     * @param info      Describes facility.
     */
    public Facility(String name, String lowLabel, String highLabel, String info) {
      super(name, lowLabel, highLabel, info);
    }

    /**
     * Copy constructor.
     *
     * @param fac Object to copy.
     */
    public Facility(Facility fac) {
      super(fac);
    }

    /**
     * Copy constructor with initial yes/no value.
     *
     * @param fac Object to copy.
     * @param yes Initial value for yes/no button.
     */
    public Facility(Facility fac, boolean yes) {
      super(fac, (yes) ? 2 : 1);
    }

  }


  /**
   * Constructor.
   *
   * @param name        Name of the beach.
   * @param description Description of the beach.
   * @param lat         Geographical latitude of the beach.
   * @param lng         Geographical longitude of the beach.
   * @param type        Type of location (just support beaches currently).
   */
  public Location(String name, String description, float lat, float lng, int type) {
    this.id = name + Integer.toString(nextId);
    this.name = name;
    this.description = description;
    this.latitude = lat;
    this.longitude = lng;
    this.type = type;

    this.facilities = new ArrayList<Facility>();
    this.conditions = new ArrayList<Condition>();

    // Give each location a unique ID.
    nextId += 1;
  }

  /**
   * Adds a new facility to the location.
   *
   * @param facility Facility to add.
   */
  public void addFacility(Facility facility) {
    facilities.add(facility);
  }

  /**
   * Adds a new condition to the location.
   *
   * @param condition Condition to add.
   */
  public void addCondition(Condition condition) {
    conditions.add(condition);
  }

  /**
   * Gets the longitude of the location.
   *
   * @return Longitude.
   */
  public float getLongitude() {
    return longitude;
  }

  /**
   * Returns latitude of location.
   *
   * @return Latitude.
   */
  public float getLatitude() {
    return latitude;
  }

  /**
   * Gets name of location.
   *
   * @return Location name.
   */
  public String getName() {
    return name;
  }

  /**
   * Provides description string.
   *
   * @return Description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets current unique ID of location.
   *
   * @return ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Gets a list of all the conditions for this location.
   *
   * @return List of conditions.
   */
  public List<Condition> getConditions() {
    return conditions;
  }

  /**
   * Gets a list of all features for the location.
   *
   * @return List of features.
   */
  public List<Facility> getFacilities() {
    return facilities;
  }

  /**
   * Provides overall reliability rating for all conditions at the location.
   * <p>
   * Provides a reliability rating [0,6] with 0 being no data available and
   * 6 being the highest reliability.
   *
   * @return Overall reliability.
   */
  public Integer getConditionReliability() {
    return 3;
  }

  /**
   * Provides overall reliability rating for all facilities at the location.
   * <p>
   * Provides a reliability rating [0,6] with 0 being no data available and
   * 6 being the highest reliability.
   *
   * @return Overall reliability for facilities.
   */
  public Integer getFacilityReliability() {
    return 6;
  }

  /**
   * Finds a feature based on its name.
   *
   * @param name Name of feature to find.
   * @return Feature object.
   */
  public Feature getFeature(String name) {

    // Look for the features in the facilities category.
    for (Feature feat : facilities) {
      if (feat.getName().equals(name)) {
        return feat;
      }
    }

    // Feature not in facilities, look in conditions...
    for (Feature feat : conditions) {
      if (feat.getName().equals(name)) {
        return feat;
      }
    }

    return null;
  }

  /**
   * Returns the total award count available for the current user
   * at the given location.
   *
   * @return Award count.
   */
  public int getTotalAwards() {
    return 38;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasParking() {
    return getFeature("Parking Facilities").getScore() == 2;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasRestrooms() {
    return getFeature("Restrooms").getScore() == 2;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasLifeguard() {
    return getFeature("Lifeguard").getScore() == 2;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean allowsDogs() {
    return getFeature("Dogs Allowed").getScore() == 2;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasBoatRamp() {
    return getFeature("Boat Ramp").getScore() == 2;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasDining() {
    return false;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean hasPublicTrans() {
    return false;
  }

  /**
   * Determine if feature is there.
   * @return Returns true if it is, false if not.
   */
  public boolean allowsCamping() {
    return getFeature("Camping").getScore() == 2;
  }

  /**
   * Returns the most popular/favorite image for this location.
   *
   * @return Path to image.
   */
  public String getImagePathMostPopular() {
    String imgPath;
    String fileName = name.toLowerCase();

    imgPath = "/assets/images/dbimg/" + fileName.replaceAll(" ", "-") + "-1.jpg";

    return imgPath;
  }

  /**
   * Determines if a given coordinate is within a specified distance of this
   * location object.
   * <p>
   * Coordinates from Google are in decimal degrees (DD).
   *
   * @param lat latitude
   * @param lng longitude
   * @return Distance from this location to the coordinates specified.
   */
  public float getDistanceFrom(float lat, float lng) {
    return (float) Math.sqrt(Math.pow(latitude - lat, 2.0f) + Math.pow(longitude - lng, 2.0f));
  }

  private String id;
  private float latitude;
  private float longitude;
  private String name;
  private String description;
  private int type;
  protected ArrayList<Facility> facilities;
  protected ArrayList<Condition> conditions;


}
