package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Feature;
import models.Location;
import models.LocationDB;
import models.Account;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.CurrentLoc;
import views.html.Index;
import views.html.LocationPage;
import views.html.SearchResults;
import views.html.HelpIcons;
import views.html.SignIn;
import views.html.NewAccount;

import java.util.List;

/**
 * Provides controller functionality.
 */
public class Application extends Controller {

  public static class SignInForm {
    public String   username;
    public String   password;

    public String validate() {
      String  authenticated;

      System.out.format("SIGN IN: %s:%s%n", username, password);
      
      if (Account.authenticate(username, password) != null) {
        authenticated = null;
      }
      else {
        authenticated = "Invalid user name or password";
      }

      return authenticated;
    }
  }

  public static class NewAccountForm {
    public String   firstName;
    public String   lastName;
    public String   email;
    public String   password;
    public String   confirmPassword;

    public String validate() {
      return null;
    }
  }

  /**
   * Gets the Account object for the currently signed in user.
   * @return Account object of current user or null if no user signed in.
   */
  public static Account getCurrentAccount() {
    String  userid = session("userid");
    Account account = null;

    if (userid != null) {
      System.out.format("--> USERID=%s%n", userid);
      account = Account.find().byId(Long.parseLong(session("userid")));
    }

    return account;
  }


  /**
   * Handles GET requests for the main/index page.
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
      List<Location>  locationList;

      /* Get a list of locations matching the search criteria and render a 
       * results page.
       */
      locationList = LocationDB.searchLocations(queryData);

      return ok(SearchResults.render(locationList, queryData));
    }
  }

  public static Result newAccount() {
    return ok(NewAccount.render(Form.form(NewAccountForm.class)));
  }

  /**
   * Handles POST request for a new account creation and validates form.
   * @return Rendered result page.
   */
  public static Result createAccount() {
    Form<NewAccountForm>  newAccountForm = Form.form(NewAccountForm.class).bindFromRequest();
    Account               newAccount;

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
   * @return Rendered Result object.
   */
  public static Result signIn() {
    return ok(SignIn.render(Form.form(SignInForm.class)));
  }

  public static Result signOut() {
    session().clear();

    return redirect( routes.Application.index());
  }

  public static Result authenticate() {
    Form<SignInForm>  signInForm = Form.form(SignInForm.class).bindFromRequest();
    Result            result;

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
   *
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
   * @param locName Name of the location to be found.
   * @return Rendered Result object.
   */
  public static Result locationPage(String locName) {
    return ok(LocationPage.render(LocationDB.getLocation(locName)));
  }

  /**
   * Handles POST request for location page updates.
   *
   * This receives data from a client to store updates for a specific feature
   * in DB and returns current information for the feature.
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
      /* The unique ID of each location is encoded into the location page HTML.
       * The Json data includes this so this code can find the correct location
       * object.
       */
      String  uwId = json.findPath("uw_id").textValue();

      // This had better be valid all the time.
      if (uwId == null) {
        return badRequest("Missing parameter in update POST request.");
      }
      else {
        ObjectNode  result = Json.newObject();
        Location    loc;
        Feature     feat;
        String      userScore;
        String[]    ids;

        /* Decode the ID field. Both the location ID and the feature name are
         * included in this data. The IDs are of the form: <location-id>_<feature-name>
         */
        ids = uwId.split("_");
        loc = LocationDB.getLocationById(ids[0]);
        feat = loc.getFeature(ids[1]);

        // User may not have set a value, only update if provided.
        userScore = json.findPath("userScore").textValue();
        if (userScore != null) {
          //TODO need to fix this logic to actually do something
        }
  
        // Return the current feature data to the client.
        //TODO - need to fix all this
        result.put("score", Integer.toString((int)feat.getScore()));
        //result.put("userScore", Integer.toString(feat.getUserScore()));
        result.put("userScore", Integer.toString(0));
        result.put("award", Integer.toString(0));
        result.put("reliability", Integer.toString(feat.getAccuracy()));

        return ok(result);
      }
    }      
  }

  /**
   * Renders a help page for the various iconns seen on the website.
   * @return Rendered result object.
   */
  public static Result helpIcons() {
    return ok(HelpIcons.render());
  }

  /**
   * Allows JavaScript to access routes. 
   *
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
