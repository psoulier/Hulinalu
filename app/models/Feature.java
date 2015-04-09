package models;

import java.util.List;
import java.util.ArrayList;

/**
 * Defines a feature of the place. 
 */
public class Feature {
  public final static int ST_UNKNOWN = 0;

  /**
   * Constructs a new feature.
   *
   * @param name Name of feature.
   * @param lowLabel Description for low scores of this feature.
   * @param highLabel Description for high scores of this feature.
   * @param info Description of what this feature is.
   */
  public Feature(String name, String lowLabel, String highLabel, String info) {
    this.name = name;
    this.info = info;
    this.userScore = ST_UNKNOWN;
    this.score = ST_UNKNOWN;
    this.lowLabel = lowLabel;
    this.highLabel = highLabel;
  }

  /**
   * Copy constructor.
   *
   * @param feat Feature object to copy.
   */
  public Feature(Feature feat) {
    this.name = feat.name;
    this.info = feat.info;
    this.score = feat.score;
    this.userScore = feat.userScore;
    this.reliability = feat.reliability;
    this.lowLabel = feat.lowLabel;
    this.highLabel = feat.highLabel;
  }


  public String getName() {
    return name;
  }

  public String getInfo() {
    return info;
  }

  public int getScore() {
    //return score;
    return 2;
  }

  public int getUserScore() {
    return userScore;
  }

  public void setUserScore(int userScore) {
    this.userScore = userScore;
  }

  public int getAward() {
    return award;
  }

  public int getReliability() {
    return reliability;
  }

  public String getLowLabel() {
    return lowLabel;
  }

  public String getHighLabel() {
    return highLabel;
  }


  private String              name;
  private String              info;
  private int                 userScore;
  private int                 score;
  private int                 award;
  private int                 reliability;
  private String              lowLabel;
  private String              highLabel;
}

