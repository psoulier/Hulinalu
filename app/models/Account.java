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
public class Account extends Model {

  @Id
  private long    id;
  private String  firstName;
  private String  lastName;
  private String  email;
  private String  mobile;
  private String  password;
  private String  roles;


  @OneToMany(mappedBy="account", cascade=CascadeType.PERSIST)
  private List<UserUpdate>      updates;

  /*
  @OneToMany(mappedBy="owner", cascade=CascadeType.PERSIST)
  private List<Photo>           photos;
  */

  @OneToMany(mappedBy="creator", cascade=CascadeType.PERSIST)
  private List<Location>        locations;


  public Account(String firstName, String lastName, String email, String mobile, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
  }

  public static Finder<Long, Account> find() {
    return new Finder<Long, Account>(Long.class, Account.class);
  }  


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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


