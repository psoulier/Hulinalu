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
  @Id
  private long    id;
  private int     score;

  @ManyToOne
  User            user;


  UserUpdate(User user) {
    this.score = -1;
    this.user = user;
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

}
