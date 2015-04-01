package models;

import java.util.ArrayList;

// POST Building: 21.297579, -157.81648

public class Location {
  public static final int   LOC_TRAIL = 1;
  public static final int   LOC_BEACH = 2;



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


  private float                 latitude;
  private float                 longitude;
  private String                name;
  private String                description;
  private int                   type;
  protected ArrayList<Feature>  features;
  protected ArrayList<Feature>  conditions;

  
}
