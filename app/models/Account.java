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
 * Contains all information associated with a user account. 
 */
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

  /**
   * Constructs a new user account.
   * @param firstName First name.
   * @param lastName Last name.
   * @param email Email address.
   * @param mobile Mobile phone number.
   * @param password Password.
   */
  public Account(String firstName, String lastName, String email, String mobile, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.mobile = mobile;
    this.password = password;
  }

  /**
   * Query routine for Ebeans.
   * @return Returns a Finder object for the query.
   */  
  public static Finder<Long, Account> find() {
    return new Finder<Long, Account>(Long.class, Account.class);
  }

  /**
   * Authenticates a username (which could be email or mobile) with password.
   * @param username Username of account (email or mobile).
   * @param password Password of user.
   */
  public static Account authenticate(String username, String password) {
    Account           account;

    account = Account.find().where().eq("email", username).findUnique();
    if (account == null) {
      account = Account.find().where().eq("mobile", username).findUnique();
    }

    if (account != null && account.getPassword().equals(password) == false) {
      account = null;
    }

    return account;
  }

  /**
   * Gets ID.
   * @return Returns ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Set ID.
   * @param id New ID.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets first name.
   * @return First name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name.
   * @param firstName First name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name.
   * @return Last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name.
   * @param lastName New last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets email address.
   * @return Returns email address.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email address.
   * @param email New email address.
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets mobile number.
   * @return Account mobile number.
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * Sets mobile number.
   * @param mobile New mobile number.
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * Gets current password.
   * @return Current password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets password.
   * @param password New password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns a list of updates associated with this account.
   * @return List of user update objects.
   */
  public List<UserUpdate> getUpdates() {
    return updates;
  }

  /**
   * Sets the user update list.
   * @param updates List of updates for this user.
   */
  public void setUpdates(List<UserUpdate> updates) {
    this.updates = updates;
  }

  /**
   * Gets a list of locations created by this user.
   * @return List of Location objects.
   */
  public List<Location> getLocations() {
    return locations;
  }

  /**
   * Sets the locations created by this user.
   * @param locations List of locations.
   */
  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  /**
   * Adds a new update for an account.
   * @param update New update to add.
   */
  public void addUpdate(UserUpdate update) {
    update.save();
  }

}


