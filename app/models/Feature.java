package models;

import java.util.List;
import java.util.ArrayList;

/**
 * Defines a feature of the place. 
 */
public class Feature {
  public final static int ST_UNKNOWN = 0;

  public Feature(String name, String[] values, String info) {
    this.name = name;
    this.info = info;
    this.userState = ST_UNKNOWN;
    this.values = new ArrayList<String>();
    this.state = ST_UNKNOWN;

    this.values.add("Unknown");
    for (String val : values) {
      System.out.format("%s %n", val);
      this.values.add(val);
    }
  }

  public Feature(Feature feat) {
    this.name = feat.name;
    this.info = feat.info;
    this.state = feat.state;
    this.values = new ArrayList<String>(feat.values);
  }


  public String getName() {
    return name;
  }

  public String getInfo() {
    return info;
  }

  public String getValue() {
    return values.get(state);
  }

  public List<String> getValues() {
    return values;
  }

  public int getReliability() {
    return reliability;
  }


  private String              name;
  private String              info;
  private int                 userState;
  private int                 state;
  private int                 reliability;
  private ArrayList<String>   values;
}

