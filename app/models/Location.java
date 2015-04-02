package models;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

// POST my office: 21.297579, -157.81648
// POST 318B: 21.31085, -157.85782
public class Location {
  public static final int   LOC_TRAIL = 1;
  public static final int   LOC_BEACH = 2;
  public static final int   LOC_BUILDING = 3;


  /**
   * Defines a feature of the place. 
   */
  public static class Feature {
    public final static int ST_UNSPECIFIED = -1;

    public Feature(String name, String description, String[] values) {
      this.name = name;
      this.description = description;
      this.userState = ST_UNSPECIFIED;
      this.values = new ArrayList<String>();

      for (String val : values) {
        System.out.format("%s %n", val);
        this.values.add(val);
      }
    }


    public String getName() {
      return name;
    }

    public List<String> getValues() {
      return values;
    }


    private String            name;
    private String            description;
    private int               userState;
    private int               state;
    private float             reliability;
    private ArrayList<String> values;
  }

  public Location(String name, String description, float lat, float lng, int type) {
    this.name = name;
    this.description = description;
    this.latitude = lat;
    this.longitude = lng;
    this.type = type;

    this.features = new ArrayList<Feature>();
    this.conditions = new ArrayList<Feature>();
  }

  public void addFeature(Feature feature) {
    features.add(feature);
  }

  public void addCondition(Feature condition) {
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

  public Feature getNextFeatureToUpdate() {
    return features.get(0);
  }

  public Feature getNextConditionToUpdate() {
    return conditions.get(0);
  }

  /**
   * Determines if a given coordinate is within a specified distance of this
   * location object.
   *
   * Coordinates from Google are in decimal degrees (DD). 
   *
   * @param lat latitude
   * @param lng longitude
   * @param range Radial distance around location (in meters)
   */
  public bool isWithin(float lat, float lng, float range) {
    float d;

    d = Math.sqrt( Math.pow(lat-latitude, 2.0f) + Math.pow(lng - longitude, 2.0f) );

//    0.00001 = 1.1132 meters

    return d < range
  }

  private float                 latitude;
  private float                 longitude;
  private String                name;
  private String                description;
  private int                   type;
  protected ArrayList<Feature>  features;
  protected ArrayList<Feature>  conditions;

  
}
