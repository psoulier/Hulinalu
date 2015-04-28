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
public class User extends Model {

  @Id
  private long    id;
  private String  email;
  private String  mobile;
  private String  password;

  @OneToMany(mappedBy="user", cascade=CascadeType.PERSIST)
  private List<UserUpdate>      updates;

  /*
  @OneToMany(mappedBy="owner", cascade=CascadeType.PERSIST)
  private List<Photo>           photos;
  */

  @OneToMany(mappedBy="creator")
  private List<Location>        locations;


  public User(String email, String mobile, String password) {
    this.email = email;
    this.mobile = mobile;
    this.password = password;
  }

  public static Finder<Long, User> find() {
    return new Finder<Long, User>(Long.class, User.class);
  }  


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<UserUpdate> getUpdates() {
    return updates;
  }

  public void setUpdates(List<UserUpdate> Updates) {
    this.updates = updates;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public void addUpdate(UserUpdate update) {
    update.save();
  }

}


