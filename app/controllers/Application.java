package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(Index.render("Your new application is ready."));
    }

    public static Result postLogin() {
      Form<ContactFormData> formData = Form.form(ContactFormData.class).bindFromRequest();
      Result                result;

      /* Check if form has any errors and return appropriate result.  */
      if (formData.hasErrors()) {
        System.out.println("Error!");
        result = badRequest(NewContact.render(formData));
      }
      else {
        ContactFormData data = formData.get();
        ContactDB.addContact(data);
        result = ok(NewContact.render(formData));
      }

      return result;
    }
    

    public static Result signUp() {
        return ok(SignUp.render("Your new application is ready."));
    }
}
