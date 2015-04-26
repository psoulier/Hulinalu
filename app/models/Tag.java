package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

import java.util.List;
import java.util.ArrayList;


/**
 * Defines a feature of a location.
 */
@Entity
public class Tag extends Model{

  public final static int UNKNOWN = -1;
  public final static int NO = 0;
  public final static int YES = 1;

  // @Column(columnDefinition = "TEXT")
  // make a string the text type which is unlimited in size (supposedly)
  //
  @Id
  private long    id;

  private String  name;
  private String  info;
  private int     score;

  @ManyToOne
  Location        location;


  /**
   * Constructs a new feature.
   * @param name      Name of feature.
   * @param info      Description of what this feature is.
   */
  public Tag(String name, String info) {
    this.name = name;
    this.info = info;
    this.score = UNKNOWN;
  }


  /**
   * Gets the name of the feature.
   * @return Returns name.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets info about the feature.
   *
   * @return Returns info.
   */
  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  /**
   * Gets the current score.
   *
   * @return Returns score.
   */
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public Location getLocation() {
    return location;
    }

  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * Gets the current reliability for this feature.
   *
   * @return Returns reliability.
   */
  public int getReliability() {
    return 0;
  }

}


