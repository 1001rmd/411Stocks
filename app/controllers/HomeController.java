package controllers;

import play.mvc.*;
import play.mvc.Http.Request;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private long sessionUserID;
	
	
    public Result index(Request request) {
        
		//Checks if the user is logged in by populating the session users ID
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
			return ok(index.render("411 Stocks"));
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
		
		
    }

}
