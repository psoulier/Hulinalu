package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Routes;
import views.html.Index;
import views.html.SignUp;
import views.formdata.Login;

import models.LocationDB;
import models.Location;

import java.util.List;

import views.html.*;

public class Application extends Controller {

    public final static String WEBNAME = "Sharesabout";

    public static Result index() {
      String queryData = Form.form().bindFromRequest().get("q");


      if (queryData == null) {
        return ok(Index.render());
      } else {
        List<Location>  locationList;

        locationList = LocationDB.searchLocations(queryData);

        return ok( SearchResults.render(locationList) );
      }
    }

    public static Result query(String queryData) {
      return ok( SignUp.render(queryData) );
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

    public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
        Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Application.currentLocation()) );
    }
}
