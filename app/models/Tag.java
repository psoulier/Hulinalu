package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

import com.avaje.ebean.Expr;

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
  private int     yes;
  private int     no;
  private int     reliability;

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
    this.yes = 0;
    this.no = 0;
    this.reliability = 0;
  }

  public static Finder<Long, Tag> find() {
    return new Finder<Long, Tag>(Long.class, Tag.class);
  }
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
   * Gets the current yes count.
   *
   * @return Returns yes count.
   */
  public int getYes() {
    return yes;
  }

  public void setYes(int yes) {
    this.yes = yes;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
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
    return reliability;
  }

  public void setReliability(int reliability) {
    this.reliability = reliability;
  }

  /**
   * Updates a tag with a yes/no score from a specific user.
   * @param user User updating the tag.
   * @param score Score to update tag with.
   */
  public void update(User user, int score) {
    UserUpdate  uu;

    // Find user updates where user matches and parentId of uu matches this tag.
    uu = UserUpdate.find().where().and( Expr.eq("user.id", user.getId()), 
        Expr.and(Expr.eq("parentId", id), Expr.eq("type", UserUpdate.TAG)) 
        ).findUnique();

    if (uu != null) {
      if (uu.getScore() == Tag.YES) {
        yes -= 1;
      }
      else {
        no -= 1;
      }

      uu.setScore(score);
    }
    else {
      uu = new UserUpdate(user, UserUpdate.TAG, id, score);
      user.addUpdate(uu);
    }

    if (score == Tag.YES) {
      yes += 1;
    }
    else {
      no += 1;
    }

    calcReliability();
    save();
    user.save();
  }

  /**
   * Calculates the reliability of the tag.
   */
  private void calcReliability() {
    float percent;

    if (yes + no == 0 || yes == no) {
      reliability = 0;
    } 
    else {
      float n = (float)yes;
  
      if (yes < no) {
        n = (float)no;
      }

      percent = n/(float)(yes + no);
      if (percent < 0.60f) reliability = 1;
      else if (percent < 0.70f) reliability = 2;
      else if (percent < 0.80f) reliability = 3;
      else if (percent < 0.90f) reliability = 4;
      else if (percent < 0.95f) reliability = 5;
      else reliability = 6;
    }
  }

}


