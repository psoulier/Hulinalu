package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Routes;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import views.html.Index;
import views.html.SignUp;
import views.formdata.Login;

import models.LocationDB;
import models.Location;

import java.util.List;

import views.html.*;

public class Application extends Controller {


    public static Result index() {
      String queryData = Form.form().bindFromRequest().get("q");


      if (queryData == null) {
        return ok(Index.render());
      } else {
        List<Location>  locationList;

        locationList = LocationDB.searchLocations(queryData);

        return ok( SearchResults.render(locationList, queryData) );
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

    public static Result update() {
  JsonNode json = request().body().asJson();

  System.out.format("Do I even get HERE?!?!!?%n");

  if(json == null) {
    return badRequest("Expecting Json data");
  } else {
    String name = json.findPath("name").textValue();
    if(name == null) {
      return badRequest("Missing parameter [name]");
    } else {
            ObjectNode result = Json.newObject();

      result.put("reliability", "2");

      return ok(result);
      //return ok("Hello " + name);
    }
  }      
      /*
      JsonNode  json = request().body().asJson();

      String  uw_id = json.findPath("uw_id").textValue();
      System.out.format("Got ID=%s%n", uw_id);

      ObjectNode result = Json.newObject();

      result.put("reliability", "2");

      return ok("{one: two}");
      */
    }

    public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
        Routes.javascriptRouter("jsRoutes", 
          controllers.routes.javascript.Application.currentLocation(),
          controllers.routes.javascript.Application.update()) );
    }
}
