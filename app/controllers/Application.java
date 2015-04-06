package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Routes;
import views.html.Index;
import views.html.SignUp;
import views.formdata.Login;

import models.LocationDB;

import views.html.*;

public class Application extends Controller {

    public final static String WEBNAME = "Sharesabout";

    public static Result index() {
      String x = Form.form().bindFromRequest().get("q");

      System.out.format("poop=%s%n", x);

      if (x == null) {
        return ok(Index.render());
      } else {
        return ok( SignUp.render(x) );
      }
    }

    public static Result query(String queryData) {
      return ok( SignUp.render(queryData) );
    }

    public static Result login() {
      return ok(Index.render());
    }

    public static Result postLocationUpdate() {
      return ok(Home.render("Your new application is ready."));
    }
    

    public static Result signUp() {
        return ok(SignUp.render("Your new application is ready."));
    }

    public static Result home() {
        return ok(Home.render("Your new application is ready."));
    }

    public static Result currentLocation(String lat, String lng) {
      return ok(CurrentLoc.render( Float.valueOf(lat), Float.valueOf(lng) ) );
    }

    public static Result locationPage(String locQuery) {
      return ok(LocationPage.render( LocationDB.getLocation(locQuery) ));
    }

    public static Result searchResults(String query) {
      return ok(SearchResult.render( LocationDB.getLocations(query) ));
    }

    public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
        Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Application.currentLocation()) );
    }
}
