package controllers;

import play.*;
import play.mvc.*;

public class UserController extends Controller{


	//This function returtns a JSon list of all users
	public Result getAll(){
		
		
		return ok("Success");
	}
	
	//This function returns a single user idnetified by their UserID
	public Result getUser(long id){
		
		return ok("Success");
		
	}
	
	//This Funtion creates a new user
	public Result addUser(){
		return ok("Success");
	}

	//This function updates user information
	public Result updateUser(){
		return ok("Success");
	}


}