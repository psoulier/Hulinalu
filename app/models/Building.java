package models;

public class Trail extends Location {
  public Trail(String name, String description, float lat, float lng) {
    super(name, description, lat, lng, LOC_BUILDING);
  }
}
