package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.Routes;
import views.html.Index;
import views.html.SignUp;
import views.formdata.Login;

import views.html.*;

public class Application extends Controller {

    public final static String WEBNAME = "Sharesabout";

    public static Result index() {
        Login   data = new Login();

        System.out.format("index: username: %s password: %s %n", data.username, data.password);

        Form<Login> formData = Form.form(Login.class).fill(data);
        System.out.format("index: username: %s password: %s %n", data.username, data.password);

        return ok(Index.render(formData));
    }

    public static Result login() {
      Form<Login>           formData = Form.form(Login.class).bindFromRequest();
      Result                result;

      /* Check if form has any errors and return appropriate result.  */
      if (formData.hasErrors()) {
        System.out.println("Error!");
        result = badRequest(Index.render(formData));
      }
      else {
        Login       data = formData.get();

        //result = ok(Index.render(formData));
        result = redirect( routes.Application.home() );
      }

      return result;
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

    public static Result javascriptRoutes() {
      response().setContentType("text/javascript");
      return ok(
        Routes.javascriptRouter("jsRoutes", controllers.routes.javascript.Application.currentLocation()) );
    }
}
