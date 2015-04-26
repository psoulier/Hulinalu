package models;

import play.db.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.Transient;

import java.util.List;
import java.util.ArrayList;


/**
 * Defines a feature of a location.
 */
@Entity
public class Feature extends Model {

  // @Column(columnDefinition = "TEXT")
  // make a string the text type which is unlimited in size (supposedly)
  //
  @Id
  private long    id;

  private String  name;
  private String  info;

  @Transient
  private boolean scoreSet;
  @Transient
  private float   score;

  /* This is kind of lame, but the other options aren't so great since the JPA Play
   * uses won't perist fixed-sized arrays. Other options:
   * - create a List and persist with a one-to-many. This seems a bit much.
   * - save as a string and parse values out of it. Seems equally lame.
   */
  private int     score1;
  private int     score2;
  private int     score3;
  private int     score4;
  private int     score5;

  private String lowLabel;
  private String highLabel;
  private String scoreValues;

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
    this.scoreSet = false;
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

  /**
   * Gets the current score.
   * @return Returns score.
   */
  public float getScore() {
    if (!scoreSet) {
      calcScore();
      scoreSet = true;
    }

    return score;
  }

  private void calcScore() {
    score = (float)(score1+score2+score3+score4+score5) / 5.0f;
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
   * Gets the current reliability for this feature.
   * @return Returns reliability.
   */
  public int getReliability() {
    return 0;
  }

  public void addScore(int score) {
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
  }
  
  public void amendScore(int oldScore, int newScore) {
    switch (oldScore) {
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

    switch (newScore) {
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
        throw new RuntimeException("Invaid new score");
    }

    calcScore();
  }
}

