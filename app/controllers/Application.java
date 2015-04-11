package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Routes;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import views.html.Index;

import models.LocationDB;
import models.Location;
import models.Feature;

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


    public static Result currentLocation(String lat, String lng) {
      return ok(CurrentLoc.render( Float.valueOf(lat), Float.valueOf(lng) ) );
    }

    public static Result locationPage(String locQuery) {
      return ok(LocationPage.render( LocationDB.getLocation(locQuery) ));
    }

    public static Result update() {
      JsonNode json = request().body().asJson();

      System.out.format("Do I even get HERE?!?!!?%n");

      if (json == null) {
        return badRequest("Expecting Json data");
      } else {
        String  uwId = json.findPath("uw_id").textValue();

        if(uwId == null) {
          return badRequest("Missing parameter in update POST request.");
        } else {
          ObjectNode  result = Json.newObject();
          Location    loc;
          Feature     feat;
          String      userScore;
          String[]    ids;

          // Update widget IDs are of the form: <location-id>_<feature-name>
          ids = uwId.split("_");
          loc = LocationDB.getLocationById(ids[0]);
          feat = loc.getFeature(ids[1]);

          userScore = json.findPath("userScore").textValue();
          if (userScore != null) {
            feat.setUserScore(Integer.parseInt(userScore));
          }


          result.put("score", Integer.toString(feat.getScore()));
          result.put("userScore", Integer.toString(feat.getUserScore()));
          result.put("award", Integer.toString(feat.getAward()));
          result.put("reliability", Integer.toString(feat.getReliability()));

          return ok(result);
        }
      }      
    }

    public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
        Routes.javascriptRouter("jsRoutes", 
          controllers.routes.javascript.Application.currentLocation(),
          controllers.routes.javascript.Application.update()) );
    }
}
