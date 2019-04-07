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
import play.i18n.MessagesApi;
import io.ebean.Finder;
import io.ebean.Ebean;
import javax.inject.Inject;
import play.i18n.MessagesApi;


public class UserController extends Controller{

	private FormFactory ff;
	private Form<User> userForm;
	private Form<User> updateForm;
	private MessagesApi messagesApi;
	private Finder<Long, User> finder = new Finder<>(User.class);
	long sessionUserID = 0;
	

	//Creates the controller and injects a FormFactory and Messages API to create a scala web form
	@Inject
	public UserController(FormFactory ff, MessagesApi ma){
		this.ff = ff;
		userForm = ff.form(User.class);
		this.messagesApi = ma;
	}
	
	//This function displays the user page
	public Result display(Request request){
		return ok(user.render(userForm, request, messagesApi.preferred(request)));
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
		
		User user = userForm.bindFromRequest(request).get();
		
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
	public Result manage(Request request){
		
		//Ensures user is logged in
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
			
			updateForm = ff.form(User.class);
			return ok(manageUser.render(updateForm, request, messagesApi.preferred(request)));
			
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
	}

	//This function updates user information
	public Result updateUser(Request request){
		
		//Gets user
		sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		User user = finder.byId(sessionUserID);
		
		//Gets the updated user from Form
		User updates = updateForm.bindFromRequest(request).get();
		
		
		
		//Finds new data and updates user
		if(!updates.name.isEmpty()){
			user.name = updates.name;
		}
		if(!updates.password.isEmpty()){
			user.password = updates.password;
		}
		
		user.save();
		
		return redirect(routes.UserController.manage());
	}
	
	//This function deletes a user
	public Result deleteUser(Request request){
		
		//Ensures user is logged in
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
			
			//Deletes User
			User user = finder.byId(sessionUserID);
			Ebean.delete(user);
			return redirect(routes.LoginController.display(false));
			
		}catch(Exception e){
			return ok();
		} 
		
		//return ok();
		
	}


}