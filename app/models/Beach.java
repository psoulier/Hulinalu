package models;

public class Beach extends Location {
  public Beach(String name, String description, float lat, float lng) {
    super(name, description, lat, lng, LOC_BEACH);
  }
}
