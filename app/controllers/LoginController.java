package controllers;

import models.*;
import views.html.*;

import play.*;
import play.mvc.*;
import play.mvc.Http.Session;
import play.mvc.Http.Request;
import io.ebean.Finder;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import javax.inject.Inject;
import java.util.Map;

public class LoginController extends Controller{

	private Form<UserLoginRequest>form;
	private MessagesApi messagesApi;
	private Finder<Long, User> finder = new Finder<>(User.class);

	//Creates the controller and injects a FormFactory
	@Inject
	public LoginController(FormFactory ff, MessagesApi ma){
		this.form = ff.form(UserLoginRequest.class);
		messagesApi = ma;
	}
	
	
	
	/*This function displays the login screen.
	* If a previous login attempt fails the boolean parameter is passed as true.
	* This displays an error message on the login page.
	* By default the parameter is set to false displaying no message   */
	public Result display(boolean error, Request request){	
		return ok(login.render(form, error, request, messagesApi.preferred(request)));
	}
	
	
	/*This fuction attempts to log the user in.
	* If the login is successful the user is stored in 
	* the session. If not the display is called again 
	* with an error message */
	public Result login(Request request){
		
		UserLoginRequest login = form.bindFromRequest(request).get();
		User user = null;
		
		//Attempts to finds user in database
		try{
			user = finder.query().where().ilike("email", login.email).findList().get(0);
		}catch(Exception e){
			//This means the user does not exist in the database
			return redirect(routes.LoginController.display(true));
		}
		
		//compares passwords
		if(user.password.equals(login.password)){
			
			//adds user to session
			return redirect(routes.HomeController.index()).addingToSession(request, "userID", Long.toString(user.id));
		}
		
		
		return redirect(routes.LoginController.display(true));
		
	}
	
	//This function logs the user out
	public Result logout(){
		return redirect(routes.LoginController.display(false)).withNewSession();
	}
	
}