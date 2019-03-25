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
import javax.inject.Inject;


public class UserController extends Controller{

	private Form<User>form;
	private Finder<Long, User> finder = new Finder<>(User.class);

	//Creates the controller and injects a FormFactory
	@Inject
	public UserController(FormFactory ff){
		this.form = ff.form(User.class);
	}
	
	//This function displays the user page
	public Result display(){
		return ok(user.render(form));
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
	
	//This funtion creates a new user
	public Result addUser(Request request){
		
		User user = form.bindFromRequest(request).get();
		user.save();		
		return ok("Success");
	}

	//This function updates user information
	public Result updateUser(){
		return ok("Success");
	}
	
	//This function deletes a user
	public Result deleteUser(long id){
		
		return ok();
	}


}