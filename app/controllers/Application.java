package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(Index.render("Your new application is ready."));
    }

    public static Result signUp() {
        return ok(SignUp.render("Your new application is ready."));
    }
}
