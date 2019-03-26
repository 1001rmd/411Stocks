package controllers;

import models.User;
import views.html.*;
import java.util.List;
import play.libs.Json;

import play.mvc.*;
import play.mvc.Http.Request;
import play.*;
import play.data.Form;
import play.data.FormFactory;
import io.ebean.Finder;
import io.ebean.Ebean;
import javax.inject.Inject;
import play.i18n.MessagesApi;


public class UserController extends Controller{

	private Form<User>form;
	private MessagesApi messagesApi;
	private Finder<Long, User> finder = new Finder<>(User.class);
	

	//Creates the controller and injects a FormFactory and Messages API to create a scala web form
	@Inject
	public UserController(FormFactory ff, MessagesApi ma){
		this.form = ff.form(User.class);
		this.messagesApi = ma;
	}
	
	//This function displays the user page
	public Result display(Request request){
		return ok(user.render(form, request, messagesApi.preferred(request)));
	}

	//This function returtns a JSon list of all users
	public Result getAll(){
		
		List<User> users = finder.all();
		return ok(Json.toJson(users));
	}
	
	//This function returns a single user idnetified by their UserID
	public Result getUser(long id){
		
		User user = finder.byId(id);
		return ok(Json.toJson(user));
		
	}
	
	//This funtion creates a new user.
	public Result addUser(Request request){
		
		User user = form.bindFromRequest(request).get();
		
		//Ensures no duplicate emails
		try{
			User checkEmail = finder.query().where().ilike("email", user.email).findList().get(0);
			if(checkEmail != null){
				return status(440, "There is already an account with that email address");
			}
		}catch(Exception e){
			//Do Nothing
			//This Exception means there are no other users with the same email
		}
		
		user.save();
		return redirect(routes.LoginController.display(false));
		
	}
	
	//This function shows the user management page
	public Result manage(){
		
		return ok("Manage");
	}

	//This function updates user information
	public Result updateUser(){
		return ok("Success");
	}
	
	//This function deletes a user
	public Result deleteUser(long id){
		
		User user = finder.byId(id);
		Ebean.delete(user);
		return ok();
	}


}