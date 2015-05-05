package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Transient;


/**
 * Contains all information for a single photo.
 */
@Entity
public class Photo extends Model {

  @Id
  private long    id;

  private long    likes;
  private String  photoId;

  @ManyToOne
  private Location  location;

  /**
   * Constructs a new photo.
   * @param photoId Unique identifying string for photo.
   */
  public Photo(String photoId) {
    this.photoId = photoId;
  }

  /**
   * Get ID.
   * @return ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets ID.
   * @param id New ID.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets number of "likes" for photo.
   * @return Number of likes.
   */
  public long getLikes() {
    return likes;
  }

  /**
   * Sets the number of likes for the photo.
   * @param likes New number of likes.
   */
  public void setLikes(long likes) {
    this.likes = likes;
  }

  /**
   * Gets unique photo ID.
   * @return Photo ID.
   */
  public String getPhotoId() {
    return photoId;
  }

  /**
   * Sets photo's ID.
   * @param photoId New photo ID.
   */
  public void setPhotoId(String photoId) {
    this.photoId = photoId;
  }

  /**
   * Gets location photo is associated with.
   * @return Location.
   */
  public Location getLoation() {
    return location;
  }

  /**
   * Sets location this photo belongs to.
   * @param location Location.
   */
  public void setLocation(Location location) {
    this.location = location;
  }
}
