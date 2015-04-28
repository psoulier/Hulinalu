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

@Entity
public class UserUpdate extends Model {
  public final static int   TAG=1;
  public final static int   FEATURE=2;
  public final static int   PHOTO=3;

  @Id
  private long    id;
  private long    parentId;
  private int     type;
  private int     score;
  private int     weight;

  @ManyToOne
  User            user;


  UserUpdate(User user, int type, long parentId, int score) {
    this.parentId = parentId;
    this.user = user;
    this.type = type;
    this.score = score;
    this.weight = 1;
  }

  public static Finder<Long, UserUpdate> find() {
    return new Finder<Long, UserUpdate>(Long.class, UserUpdate.class);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

}
