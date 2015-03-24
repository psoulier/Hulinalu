package views.formdata;

import play.data.validation.ValidationError;

import java.util.List;
import java.util.ArrayList;


public class Login {
  public String     username;
  public String     password;

  public List<ValidationError> validate() {
    System.out.format("validate: %s:%s%n", username, password);
    return null;
  }
}

