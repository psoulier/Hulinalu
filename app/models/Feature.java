package models;

import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Application;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


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
  private long id;

  private String name;
  private String info;


  /* This is kind of lame, but the other options aren't so great since the JPA Play
   * uses won't perist fixed-sized arrays. Other options:
   * - create a List and persist with a one-to-many. This seems a bit much.
   * - save as a string and parse values out of it. Seems equally lame.
   */
  private int score;
  private int score1;
  private int score2;
  private int score3;
  private int score4;
  private int score5;
  private int accuracy;

  private String scoreValues;

  @ManyToOne
  private Location location;


  /**
   * Constructs a new feature.
   *
   * @param name      Name of feature.
   * @param info      Description of what this feature is.
   * @param scoreValues String of semicolon separated labels for each score bucket.
   */
  public Feature(String name, String info, String scoreValues) {
    this.name = name;
    this.info = info;
    this.scoreValues = scoreValues;
    this.score = 0;
    this.score1 = 0;
    this.score2 = 0;
    this.score3 = 0;
    this.score4 = 0;
    this.score5 = 0;
    this.accuracy = 0;
  }

  /**
   * Query routine for Ebeans.
   *
   * @return Returns a Finder object for the query.
   */
  public static Finder<Long, Feature> find() {
    return new Finder<Long, Feature>(Long.class, Feature.class);
  }

  /**
   * Gets ID.
   *
   * @return ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets ID.
   *
   * @param id New ID.
   */
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

  /**
   * Sets the name of the feature.
   *
   * @param name New name.
   */
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

  /**
   * Sets info for the feature.
   *
   * @param info New info.
   */
  public void setInfo(String info) {
    this.info = info;
  }

  /**
   * Collect data relevant to the associated object for the client.
   *
   * @param data Json object to populate.
   */
  public void fetchUpdate(ObjectNode data) {
    Account account = Application.getCurrentAccount();
    UserUpdate uu;
    int userScore = 0;
    String scores = "";

    if (account != null) {
      // Need to find a user update from this user, for this feature, and
      // a FEATURE type.
      uu = UserUpdate.find().where().and(
          Expr.eq("account.id", account.getId()), Expr.and(
              Expr.eq("parentId", id), Expr.eq("type", UserUpdate.FEATURE)
          )
      ).findUnique();

      if (uu != null) {
        userScore = uu.getScore();
      }
    }

    scores += Integer.toString(score1) + ";";
    scores += Integer.toString(score2) + ";";
    scores += Integer.toString(score3) + ";";
    scores += Integer.toString(score4) + ";";
    scores += Integer.toString(score5);


    data.put("score", Integer.toString(score));
    data.put("userScore", Integer.toString(userScore));
    data.put("accuracy", Integer.toString(accuracy));

    if (score == 0) {
      data.put("scoreLabel", "No Info");
    }
    else {
      String[] vals = scoreValues.split(";");
      data.put("scoreLabel", vals[score - 1]);
    }

    data.put("scoreList", scores);
  }

  /**
   * Gets the score previously computed using the score counts.
   *
   * @return Calculated score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Sets score.
   *
   * @param score New score.
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Sets score count.
   *
   * @return Score count.
   */
  public int getScore1() {
    return score1;
  }

  /**
   * Sets score count.
   *
   * @param score New score.
   */
  public void setScore1(int score) {
    this.score1 = score;
  }

  /**
   * Sets score count.
   *
   * @return Score count.
   */
  public int getScore2() {
    return score2;
  }

  /**
   * Sets score count.
   *
   * @param score New score.
   */
  public void setScore2(int score) {
    this.score2 = score;
  }

  /**
   * Sets score count.
   *
   * @return Score count.
   */
  public int getScore3() {
    return score3;
  }

  /**
   * Sets score count.
   *
   * @param score New score.
   */
  public void setScore3(int score) {
    this.score3 = score;
  }

  /**
   * Sets score count.
   *
   * @return Score count.
   */
  public int getScore4() {
    return score4;
  }

  /**
   * Sets score count.
   *
   * @param score New score.
   */
  public void setScore4(int score) {
    this.score4 = score;
  }

  /**
   * Sets score count.
   *
   * @return Score count.
   */
  public int getScore5() {
    return score5;
  }

  /**
   * Sets score count.
   *
   * @param score New score.
   */
  public void setScore5(int score) {
    this.score5 = score;
  }

  /**
   * Get score values.
   *
   * @return Semicolon separated list of descriptive label for each score bucket.
   */
  public String getScoreValues() {
    return scoreValues;
  }

  /**
   * Sets score value.
   *
   * @param scoreValues A semicolon separated list of labels for each score bucket.
   */
  public void setScoreValues(String scoreValues) {
    this.scoreValues = scoreValues;
  }

  /**
   * Returns the label for the "low" end of the scale.
   *
   * @return Low label.
   */
  public String getLowLabel() {
    return scoreValues.split(";")[0];
  }

  /**
   * Returns the label for the "high" end of the scale.
   *
   * @return High label.
   */
  public String getHighLabel() {
    return scoreValues.split(";")[4];
  }

  /**
   * Gets the current accuracy for this feature.
   *
   * @return Returns accuracy.
   */
  public int getAccuracy() {
    return accuracy;
  }

  /**
   * Sets accuracy value.
   *
   * @param accuracy New value.
   */
  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }

  /**
   * Updates a tag with a yes/no score from a specific user.
   *
   * @param account User updating the tag.
   * @param score   Score to update tag with.
   */
  public void update(Account account, int score) {
    UserUpdate uu;

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
          break;
        case 2:
          score2 -= 1;
          break;
        case 3:
          score3 -= 1;
          break;
        case 4:
          score4 -= 1;
          break;
        case 5:
          score5 -= 1;
          break;
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
        break;
      case 2:
        score2 += 1;
        break;
      case 3:
        score3 += 1;
        break;
      case 4:
        score4 += 1;
        break;
      case 5:
        score5 += 1;
        break;
      default:
        throw new RuntimeException("Invalid score value");
    }

    calcScore();

    save();
    account.save();
    uu.save();
  }

  /**
   * Calculates score and accuracy based on score buckets.
   */
  private void calcScore() {
    double total;

    total = (float) (score1 + score2 + score3 + score4 + score5);

    if (total == 0) {
      score = 0;
      accuracy = 0;
    }
    else {
      double sd,
          mean;

      mean = (double) (score1 + 2 * score2 + 3 * score3 + 4 * score4 + 5 * score5) / total;
      score = (int) Math.round(mean);

      /* Why the dumb-ass "2.0 + 2.0" stuff you ask? Because checkstyle is
       * freak 'n annoying with constants sometimes, that's why.  Seems to
       * be happy if we just use 1 or 2 tho.
       */
      sd = Math.pow(score1 * (1.0 - mean), 2.0);
      sd += Math.pow(score2 * (2.0 - mean), 2.0);
      sd += Math.pow(score3 * (1.0 + 2.0 - mean), 2.0);
      sd += Math.pow(score4 * (2.0 + 2.0 - mean), 2.0);
      sd += Math.pow(score5 * (2.0 + 2.0 + 1.0 - mean), 2.0);

      if (sd < VAR_EXCELLENT) {
        accuracy = 5;
      }
      else if (sd < VAR_GOOD) {
        accuracy = 4;
      }
      else if (sd < VAR_AVERAGE) {
        accuracy = 3;
      }
      else if (sd < VAR_MARGINAL) {
        accuracy = 2;
      }
      else {
        accuracy = 1;
      }
    }
  }

}

