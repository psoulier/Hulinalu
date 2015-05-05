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
 * Object to contain a variety of user updates to store persistently.
 */
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

  @ManyToOne
  Account         account;

  /**
   * Constructs a new UserUpdate object.
   * @param account Account update is associated with.
   * @param type The type of update.
   * @param parentId The feature, tag, photo, etc. that this update is associated with.
   * @param score Score associated with the update.
   */
  UserUpdate(Account account, int type, long parentId, int score) {
    this.parentId = parentId;
    this.account = account;
    this.type = type;
    this.score = score;
  }

  /**
   * Query routine for Ebeans.
   * @return Returns a Finder object for the query.
   */
  public static Finder<Long, UserUpdate> find() {
    return new Finder<Long, UserUpdate>(Long.class, UserUpdate.class);
  }

  /**
   * Gets ID.
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
   * Gets parent object DB ID.
   * @return Parent ID.
   */
  public long getParentId() {
    return parentId;
  }

  /**
   * Sets parent ID.
   * @param parentId New parent ID.
   */
  public void setParentId(long parentId) {
    this.parentId = parentId;
  }

  /**
   * Gets type.
   * @return Type value.
   */
  public int getType() {
    return type;
  }

  /**
   * Sets type.
   * @param type New type value.
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Gets score value.
   * @return The score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets score value.
   * @param score New score.
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Gets account this update was made by.
   * @return Account object.
   */
  public Account getAccount() {
    return account;
  }

  /**
   * Sets account update was associated with.
   * @param account Account.
   */
  public void setAccount(Account account) {
    this.account = account;
  }
}
