package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


/**
 * Defines a location.
 * A location contains all the static information necessary to describe a
 * location.
 */
@Entity
public class Location extends Model {
  /**
   * Constant ID for beaches.
   */
  public static final int LOC_BEACH = 1;

  private static final String DEFAULT_SCORE_VALUES = "Poor;Marginal;Average;Good;Great";


  @Id
  private long id;
  private float latitude;
  private float longitude;
  private String name;
  private String description;
  private int type;

  @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
  private List<Feature> features;

  @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
  private List<Tag> tags;

  @OneToMany(mappedBy = "location", cascade = CascadeType.PERSIST)
  private List<Photo> photos;

  @ManyToOne
  private Account creator;


  /**
   * Constructor.
   * @param creator     Account associated to user creating location.
   * @param name        Name of the beach.
   * @param description Description of the beach.
   * @param lat         Geographical latitude of the beach.
   * @param lng         Geographical longitude of the beach.
   */
  public Location(Account creator, String name, String description, float lat, float lng) {
    this.creator = creator;
    this.name = name;
    this.description = description;
    this.latitude = lat;
    this.longitude = lng;
    this.type = LOC_BEACH;

    this.features = new ArrayList<Feature>();
    this.tags = new ArrayList<Tag>();


    // Every location has the same facilities, activities, tags, etc. Create
    // them all here.
    tags.add(new Tag(Tag.PARKING, "This beach has a parking lot facility."));
    tags.add(new Tag(Tag.DOGS, "Dogs are allowed at this beach."));
    tags.add(new Tag(Tag.BOATRAMP, "A boat ramp is available."));
    tags.add(new Tag(Tag.LIFEGUARD, "The beach is monitored by lifeguards."));
    tags.add(new Tag(Tag.RESTROOMS, "Public restrooms are present."));
    tags.add(new Tag(Tag.SHOWERS, "Public showers are available."));
    tags.add(new Tag(Tag.CAMPING, "Camp sites are located at or nearby."));
    tags.add(new Tag(Tag.PUBLICTRANS, "Public transportation available within walking distance."));

    features.add(new Feature("Snorkeling", "Potential quality of snorkeling.", DEFAULT_SCORE_VALUES));
    features.add(new Feature("Surfing", "Potential quality of surfing.", DEFAULT_SCORE_VALUES));
    features.add(new Feature("Fishing", "Fishing quality around this beach.", DEFAULT_SCORE_VALUES));
    features.add(new Feature("Swimming", "A good beach to go swiiming.", DEFAULT_SCORE_VALUES));
  }

  /**
   * Creates Finder object for DB queries.
   * @return Finder object.
   */
  public static Finder<Long, Location> find() {
    return new Finder<Long, Location>(Long.class, Location.class);
  }

  /**
   * Gets current unique ID of location.
   *
   * @return ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets ID.
   *
   * @param id New ID.
   */
  public void setId(long id) {
    this.id = id;
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
   * Sets location longitude.
   *
   * @param longitude New longitude value.
   */
  public void setLongitude(float longitude) {
    this.longitude = longitude;
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
   * Sets location latitude.
   *
   * @param latitude New latitude value.
   */
  public void setLatitude(float latitude) {
    this.latitude = latitude;
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
   * Sets name of location.
   *
   * @param name New name.
   */
  public void setName(String name) {
    this.name = name;
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
   * Sets location description.
   *
   * @param description New description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets a list of feature objects.
   *
   * @return List of features.
   */
  public List<Feature> getFeatures() {
    return features;
  }

  /**
   * Sets feature list.
   *
   * @param features List of features.
   */
  public void setFeatures(List<Feature> features) {
    this.features = features;
  }

  /**
   * Gets tag list for location.
   *
   * @return List of tags for this location.
   */
  public List<Tag> getTags() {
    return tags;
  }

  /**
   * Sets tags for this location.
   *
   * @param tags List of tags.
   */
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  /**
   * Gets all photos for this location.
   *
   * @return List of photos for this location.
   */
  public List<Photo> getPhotos() {
    return photos;
  }

  /**
   * Sets photo list for location.
   *
   * @param photos List of photos.
   */
  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

  /**
   * Gets the account/user that originally created this location.
   *
   * @return Account object of user that created location.
   */
  public Account getCreator() {
    return creator;
  }

  /**
   * Sets creator of location.
   *
   * @param creator Creator of location.
   */
  public void setCreator(Account creator) {
    this.creator = creator;
  }

  /**
   * Gets the type of location.
   *
   * @return Returns an enumerated type for the location.
   */
  public int getType() {
    return type;
  }

  /**
   * Sets location type.
   *
   * @param type New type.
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Finds a feature based on its name.
   * @param name String name of feature to get.
   * @return Feature object.
   */
  public Feature getFeature(String name) {

    // Look for the features in the facilities category.
    for (Feature feat : features) {
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
    return 0;
  }

  /**
   * Based on a tag string name, determines if this location has the tag with value YES.
   *
   * @param tagName Tag name to check.
   * @return Returns true if location has this tag set to YES, false if not.
   */
  public boolean hasTag(String tagName) {
    for (Tag tag : tags) {
      if (tag.getName().equals(tagName)) {
        return tag.getValue() == Tag.YES;
      }
    }

    throw new RuntimeException("Requested tag does not exist.");
  }

  /**
   * Determine if feature is there.
   *
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


}
