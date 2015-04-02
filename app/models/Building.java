package models;

public class Building extends Location {
  public Building(String name, String description, float lat, float lng) {
    super(name, description, lat, lng, LOC_BUILDING);
  }
}
