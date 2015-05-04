package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import com.avaje.ebean.Expr;

import controllers.Application;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;


/**
 * Defines a feature of a location.
 */
@Entity
public class Feature extends Model implements UpdateInterface {
  private static final double VAR_EXCELLENT = 0.5;
  private static final double VAR_GOOD = 0.7;
  private static final double VAR_AVERAGE = 1.0;
  private static final double VAR_MARGINAL = 1.5;
  private static final double VAR_POOR = 2.0;

  // @Column(columnDefinition = "TEXT")
  // make a string the text type which is unlimited in size (supposedly)
  //
  @Id
  private long    id;

  private String  name;
  private String  info;


  /* This is kind of lame, but the other options aren't so great since the JPA Play
   * uses won't perist fixed-sized arrays. Other options:
   * - create a List and persist with a one-to-many. This seems a bit much.
   * - save as a string and parse values out of it. Seems equally lame.
   */
  private int     score;
  private int     score1;
  private int     score2;
  private int     score3;
  private int     score4;
  private int     score5;
  private int     accuracy;

  private String  lowLabel;
  private String  highLabel;
  private String  scoreValues;

  @ManyToOne
  private Location  location;


  /**
   * Constructs a new feature.
   *
   * @param name      Name of feature.
   * @param lowLabel  Description for low scores of this feature.
   * @param highLabel Description for high scores of this feature.
   * @param info      Description of what this feature is.
   */
  public Feature(String name, String info, String lowLabel, String highLabel, String scoreValues) {
    this.name = name;
    this.info = info;
    this.lowLabel = lowLabel;
    this.highLabel = highLabel;
    this.scoreValues = scoreValues;
  }

  public static Finder<Long, Feature> find() {
    return new Finder<Long, Feature>(Long.class, Feature.class);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the name of the feature.
   *
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

  public int getValue() {
    return score;
  }

  public String getValueText() {
    if (score == 0) {
      return "No Info";
    }
    else {
      String[] values = scoreValues.split(";");

      return values[score - 1];
    }
  }

  public String getValueList() {
    String  values = "";

    values += Integer.toString(score1) + ";";
    values += Integer.toString(score2) + ";";
    values += Integer.toString(score3) + ";";
    values += Integer.toString(score4) + ";";
    values += Integer.toString(score5);

    return values;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore1() {
    return score1;
  }

  public void setScore1(int score) {
    this.score1 = score;
  }

  public int getScore2() {
    return score2;
  }

  public void setScore2(int score) {
    this.score2 = score;
  }

  public int getScore3() {
    return score3;
  }

  public void setScore3(int score) {
    this.score3 = score;
  }

  public int getScore4() {
    return score4;
  }

  public void setScore4(int score) {
    this.score4 = score;
  }

  public int getScore5() {
    return score5;
  }

  public void setScore5(int score) {
    this.score5 = score;
  }




  /**
   * Returns the label for the "low" end of the scale.
   * @return Low label.
   */
  public String getLowLabel() {
    return lowLabel;
  }

  public void setLowLabel(String label) {
    lowLabel = label;
  }

  /**
   * Returns the label for the "high" end of the scale.
   * @return High label.
   */
  public String getHighLabel() {
    return highLabel;
  }

  public void setHighLabel(String label) {
    highLabel = label;
  }

  /**
   * Gets the current accuracy for this feature.
   * @return Returns accuracy.
   */
  public int getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  public int getUserValue() {
    Account     account = Application.getCurrentAccount();
    UserUpdate  uu;
    int         score = 0;

    if (account != null) {
      // Need to find a user update from this user, for this feature, and
      // a FEATURE type.
      uu = UserUpdate.find().where().and( 
          Expr.eq("account.id", account.getId()), Expr.and(
            Expr.eq("parentId", id), Expr.eq("type", UserUpdate.FEATURE)
            )
          ).findUnique();
      
      if (uu != null) {
        score = uu.getScore();
      }
    }

    return score;
  }

  public void update(Account account, int score) {
    UserUpdate  uu;

    // Need to find a user update from this user, for this feature, and
    // a FEATURE type.
    uu = UserUpdate.find().where().and( 
        Expr.eq("account.id", account.getId()), Expr.and(
          Expr.eq("parentId", id), Expr.eq("type", UserUpdate.FEATURE)
          )
        ).findUnique();

    if (uu != null) {
      // Remove the user's previous update from the tabulation.
      switch (uu.getScore()) {
        case 1:
          score1 -= 1;
          break ;
        case 2:
          score2 -= 1;
          break ;
        case 3:
          score3 -= 1;
          break ;
        case 4:
          score4 -= 1;
          break ;
        case 5:
          score5 -= 1;
          break ;
        default:
          throw new RuntimeException("Invaid old score");
      }

      uu.setScore(score);
    }
    else {
      uu = new UserUpdate(account, UserUpdate.FEATURE, id, score);
      account.addUpdate(uu);
    }

    // Add the new update from the user to the tabulation.
    switch (score) {
      case 1:
        score1 += 1;
        break ;
      case 2:
        score2 += 1;
        break ;
      case 3:
        score3 += 1;
        break ;
      case 4:
        score4 += 1;
        break ;
      case 5:
        score5 += 1;
        break ;
      default:
        throw new RuntimeException("Invalid score value");
    }

    calcScore();

    save();
    account.save();
    uu.save();
  }
  
  private void calcScore() {
    double  total;

    total = (float)(score1 + score2 + score3 + score4 + score5);

    if (total == 0) {
      score = 0;
      accuracy = 0;
    }
    else {
      double  sd,
              mean;

      mean = (double)(score1 + 2*score2 + 3*score3 + 4*score4 + 5*score5)/total;
      score = (int)Math.round(mean);
      sd = Math.pow(score1*(1.0-mean), 2.0);
      sd += Math.pow(score2*(2.0-mean), 2.0);
      sd += Math.pow(score3*(3.0-mean), 2.0);
      sd += Math.pow(score4*(4.0-mean), 2.0);
      sd += Math.pow(score5*(5.0-mean), 2.0);

      if (sd < VAR_EXCELLENT) accuracy = 5;
      else if (sd < VAR_GOOD) accuracy = 4;
      else if (sd < VAR_AVERAGE) accuracy = 3;
      else if (sd < VAR_MARGINAL) accuracy = 2;
      else accuracy = 1;
    }
  }

}

