package models;

/**
 * Defines a beach location.
 */
public class Beach extends Location {
  /**
   * Constructs a new beach location object.
   *
   * @param name        Name of the beach.
   * @param description Description of the beach.
   * @param lat         Geographical latitude of the beach.
   * @param lng         Geographical longitude of the beach.
   */
  public Beach(String name, String description, float lat, float lng) {
    // Just call the super-class constructor for now.
    super(name, description, lat, lng, LOC_BEACH);
  }
}
