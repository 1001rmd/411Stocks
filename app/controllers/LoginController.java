package controllers;

import models.*;
import views.html.*;

import play.mvc.*;
import play.mvc.Http.Request;
import io.ebean.Finder;
import play.data.Form;
import play.data.FormFactory;
import javax.inject.Inject;

public class LoginController extends Controller{

	private Form<UserLoginRequest>form;
	private Finder<Long, User> finder = new Finder<>(User.class);

	//Creates the controller and injects a FormFactory
	@Inject
	public LoginController(FormFactory ff){
		this.form = ff.form(UserLoginRequest.class);
	}
	
	
	
	/*This function displays the login screen.
	* If a previous login attempt fails the boolean parameter is passed as true.
	* This displays an error message on the login page.
	* By default the parameter is set to false displaying no message   */
	public Result display(boolean error){	
		return ok(login.render(form, error));
	}
	
	
	/*This fuction attempts to log the user in.
	* If the login is successful the user is stored in 
	* the session. If not the display is called again 
	* with an error message */
	public Result login(Request request){
		
		return ok("Sucess");
	}
}