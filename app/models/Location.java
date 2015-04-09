package models;

import play.mvc.Controller;
import play.Routes;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;


/**
 * Defines a location.
 */
public class Location {
  public static final int   LOC_TRAIL = 1;
  public static final int   LOC_BEACH = 2;
  public static final int   LOC_BUILDING = 3;

  public static int nextId = 1;

  public enum ImageQualifier {
    MOST_RECENT,
    MOST_POPULAR
  }

  public static final float DD_PER_METER = 0.00001f / 1.1132f;

  public static class Condition extends Feature {
    public Condition(String name, String lowLabel, String highLabel, String info) {
      super(name, lowLabel, highLabel, info);
    }

    public Condition(Condition cond) {
      super(cond);
    }
  }

  public static class Facility extends Feature {
    public Facility(String name, String lowLabel, String highLabel, String info) {
      super(name, lowLabel, highLabel, info);
    }

    public Facility(Facility fac) {
      super(fac);
    }
  }


  /**
   * Constructor.
   *
   * @param name
   * @param description
   * @param lat
   * @param lng
   * @param type
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

    nextId += 1;
  }

  public void addFacility(Facility facility) {
    facilities.add(facility);
  }

  public void addCondition(Condition condition) {
    conditions.add(condition);
  }

  public float getLongitude() {
    return longitude;
  }

  public float getLatitude() {
    return latitude;
  }

  public String getName() {
    return name;
  }

  public Boolean hasFeaturesToUpdate() {
    return !facilities.isEmpty();
  }

  public Feature getNextFeatureToUpdate() {
    return facilities.get(0);
  }

  public Feature getNextConditionToUpdate() {
    return conditions.get(0);
  }

  public String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public List<Facility> getFacilities() {
    return facilities;
  }

  public Integer getConditionReliability() {
    return 3;
  }

  public Integer getFacilityReliability() {
    return 6;
  }

  public Integer getUpdateCount(int minutes) {
    return 13;
  }

  public Feature getFeature(String name) {

    // Look for the features in the facilities category.
    for (Feature feat : facilities) {
      if ( feat.getName().equals(name) ) {
        return feat;
      }
    }

    // Feature not in facilities, look in conditions...
    for (Feature feat : conditions) {
      if ( feat.getName().equals(name) ) {
        return feat;
      }
    }

    return null;
  }

  public String getImagePathMostPopular(int i) {
    String  imgPath = "";
    
    imgPath = "/assets/images/dbimg/0000-0000-0000-1234.jpg";


    return imgPath;
  }

  /**
   * Determines if a given coordinate is within a specified distance of this
   * location object.
   *
   * Coordinates from Google are in decimal degrees (DD). 
   *
   * @param lat latitude
   * @param lng longitude
   * @param radius Radial distance around location (in meters)
   */
  public float getDistanceFrom(float lat, float lng) {
    return (float)Math.sqrt( Math.pow(latitude - lat, 2.0f) + Math.pow(longitude - lng, 2.0f) );
  }

  private String                  id;
  private float                   latitude;
  private float                   longitude;
  private String                  name;
  private String                  description;
  private int                     type;
  protected ArrayList<Facility>   facilities;
  protected ArrayList<Condition>  conditions;

  
}
