package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Transient;



@Entity
public class Photo extends Model {

  @Id
  private long    id;

  private long    likes;
  private String  photoId;
  private String  tags;     // Tags that may associate a photo to a particular feature/tag/etc.

  @ManyToOne
  private Location  location;

  
  public Photo(String photoId) {
    this.photoId = photoId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLikes() {
    return likes;
  }

  public void setLikes(long likes) {
    this.likes = likes;
  }

  public String getPhotoId() {
    return photoId;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public void setPhotoId(String photoId) {
    this.photoId = photoId;
  }

  public Location getLoation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public User getOwner() {
    return null;
  }

  public void setOwner(User owner) {
  }
}
