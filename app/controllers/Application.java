package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Account;
import models.Feature;
import models.Location;
import models.LocationDB;
import models.Tag;
import models.UpdateInterface;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.CurrentLoc;
import views.html.HelpIcons;
import views.html.Index;
import views.html.LocationPage;
import views.html.NewAccount;
import views.html.NewLocation;
import views.html.SearchResults;
import views.html.SignIn;

import java.util.List;

/**
 * Provides controller functionality.
 */
public class Application extends Controller {

  /**
   * Form to handle form data from sign in page.
   */
  public static class SignInForm {
    /**
     * Either email or mobile number for sign in.
     */
    public String username;

    /**
     * Password that goes with username.
     */
    public String password;

    /**
     * Validates user sign in data.
     *
     * @return Returns null if credentials ok or an error string if not.
     */
    public String validate() {
      String authenticated;

      if (Account.authenticate(username, password) != null) {
        authenticated = null;
      }
      else {
        authenticated = "Invalid user name or password";
      }

      return authenticated;
    }
  }

  /**
   * Contains form data for a new account.
   */
  public static class NewAccountForm {
    /**
     * First name for account.
     */
    public String firstName;

    /**
     * Last name for account.
     */
    public String lastName;

    /**
     * Email address for account.
     */
    public String email;

    /**
     * Password for account.
     */
    public String password;

    /**
     * Confirm password.
     */
    public String confirmPassword;

    /**
     * Performs validation of new account data.
     *
     * @return Returns null if ok, or string if an error occurred.
     */
    public String validate() {
      return null;
    }
  }

  public static class NewLocationForm {
    public String name;
    public String descirption;

    public String validate() {
      return null;
    }
  }

  /**
   * Gets the Account object for the currently signed in user.
   *
   * @return Account object of current user or null if no user signed in.
   */
  public static Account getCurrentAccount() {
    String userid = session("userid");
    Account account = null;

    if (userid != null) {
      account = Account.find().byId(Long.parseLong(session("userid")));
    }

    return account;
  }


  /**
   * Handles GET requests for the main/index page.
   *
   * @return Return a Result object for the rendered page.
   */
  public static Result index() {
    String queryData = Form.form().bindFromRequest().get("q");

    /* The index page can be accessed two ways: by going to the home page
     * or with additional search text tagged onto the get request (e.g.: from
     * a link bookmark). If the query data is an empty string, the normal 
     * index page is shown, otherwise a search results page is rendered.
     */
    if (queryData == null) {
      return ok(Index.render());
    }
    else {
      List<Location> locationList;

      /* Get a list of locations matching the search criteria and render a 
       * results page.
       */
      locationList = LocationDB.searchLocations(queryData);

      return ok(SearchResults.render(locationList, queryData));
    }
  }

  /**
   * Renders the account creation page.
   *
   * @return Rendered Result object.
   */
  public static Result newAccount() {
    return ok(NewAccount.render(Form.form(NewAccountForm.class)));
  }


  public static Result newLocation() {
    return ok(NewLocation.render(Form.form(NewLocationForm.class)));
  }

  /**
   * Handles POST request for a new account creation and validates form.
   *
   * @return Rendered result page.
   */
  public static Result createAccount() {
    Form<NewAccountForm> newAccountForm = Form.form(NewAccountForm.class).bindFromRequest();
    Account newAccount;

    newAccount = new Account(newAccountForm.get().firstName,
        newAccountForm.get().lastName,
        newAccountForm.get().email,
        "",
        newAccountForm.get().password);

    LocationDB.addAccount(newAccount);

    session().clear();
    session("userid", Long.toString(newAccount.getId()));
    return redirect(routes.Application.index());
  }

  /**
   * Provides user sign in.
   *
   * @return Rendered Result object.
   */
  public static Result signIn() {
    return ok(SignIn.render(Form.form(SignInForm.class)));
  }

  /**
   * Handles user sign-out.
   *
   * @return Result object.
   */
  public static Result signOut() {
    session().clear();

    return redirect(routes.Application.index());
  }

  /**
   * Performs user/account authentication.
   *
   * @return Result object.
   */
  public static Result authenticate() {
    Form<SignInForm> signInForm = Form.form(SignInForm.class).bindFromRequest();
    Result result;

    if (signInForm.hasErrors()) {
      result = badRequest(SignIn.render(signInForm));
    }
    else {
      Account account;

      account = Account.authenticate(signInForm.get().username, signInForm.get().password);
      session().clear();
      session("userid", Long.toString(account.getId()));

      result = redirect(routes.Application.index());
    }

    return result;
  }

  /**
   * Renders a portion of the index page depending on where the user is located.
   * <p>
   * This is an Ajax GET request that is called once the client's location is
   * determined. This routine is initiated from JavaScript. When the index page
   * loads, there is an indicator. Once this GET request is called, that message
   * is replaced by whatever this method returns.
   *
   * @param lat Client's current latitude.
   * @param lng Client's current longitude.
   * @return Rendered Result object.
   */
  public static Result currentLocation(String lat, String lng) {
    return ok(CurrentLoc.render(Float.valueOf(lat), Float.valueOf(lng)));
  }

  /**
   * Returns a specific location page for the given location name.
   *
   * @param locId Name of the location to be found.
   * @return Rendered Result object.
   */
  public static Result locationPage(long locId) {
    return ok(LocationPage.render(Location.find().byId(locId)));
  }

  /**
   * Handles POST request for location page updates.
   * <p>
   * This receives data from a client to store updates for a specific feature
   * in DB and returns current information for the feature.
   *
   * @return Rendered Result object.
   */
  public static Result update() {
    JsonNode json = request().body().asJson();

    // Decode the Json POST request data containing the update information. 
    // This should never be null.
    if (json == null) {
      return badRequest("Expecting Json data");
    }
    else {
      Account account = getCurrentAccount();

      /* The unique ID of each location is encoded into the location page HTML.
       * The Json data includes this so this code can find the correct location
       * object.
       */
      String uwId = json.findPath("uw_id").textValue();

      // This had better be valid all the time.
      if (uwId == null) {
        return badRequest("Missing parameter in update POST request.");
      }
      else {
        ObjectNode result = Json.newObject();
        UpdateInterface update;
        int userScore;
        String[] typeId;


        /* Decode the ID field. Both the location ID and the feature name are
         * included in this data. The IDs are of the form: <location-id>_<feature-name>
         */
        typeId = uwId.split("-");


        if (typeId[0].equals("tag")) {
          update = Tag.find().byId(Long.parseLong(typeId[1]));
        }
        else if (typeId[0].equals("feature")) {
          update = Feature.find().byId(Long.parseLong(typeId[1]));
        }
        else {
          throw new RuntimeException("Unknown type tag.");
        }

        userScore = Integer.parseInt(json.findPath("userScore").textValue());

        // User may not have set a value, only update if provided.
        if (userScore != 0 && account != null) {
          update.update(account, userScore);
        }

        update.fetchUpdate(result);

        // Return the current feature data to the client.
        /*
        result.put("score", Integer.toString(update.getValue()));
        result.put("userScore", Integer.toString(update.getUserValue()));
        result.put("award", Integer.toString(0));
        result.put("accuracy", Integer.toString(update.getAccuracy()));
        result.put("scoreLabel", update.getValueText());
        result.put("scoreList", update.getValueList());
        */

        return ok(result);
      }
    }
  }

  /**
   * Renders a help page for the various iconns seen on the website.
   *
   * @return Rendered result object.
   */
  public static Result helpIcons() {
    return ok(HelpIcons.render());
  }

  /**
   * Allows JavaScript to access routes.
   * <p>
   * This is necessary to allow JavaScript issue GET/POST requests to the
   * correct route.
   *
   * @return Result object.
   */
  public static Result javascriptRoutes() {
    response().setContentType("text/javascript");
    return ok(
        // Every route accessible to JavaScript needs to be added here.
        Routes.javascriptRouter("jsRoutes",
            controllers.routes.javascript.Application.currentLocation(),
            controllers.routes.javascript.Application.update()));
  }
}
