package controllers;

import models.*;
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

public class PortfolioController extends Controller{


	private Finder<Long, User> userFinder = new Finder<>(User.class);
	private Finder<Long, Portfolio> portFinder = new Finder<>(Portfolio.class);
	private StockDataController api;
	private long sessionUserID;
	
	@Inject
	public PortfolioController(StockDataController dataController){
		api = dataController;
	}
	
	
	
	public Result display(Request request){
		
		//Checks to make sure the user is logged in
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
		//get user
		User user = userFinder.byId(sessionUserID);
		return ok(userPortfolios.render(user));
		
	}
	
	
	
	

	public Result displayPortfolio(long id, Request request){
		
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
		
		Portfolio port = portFinder.byId(id);
		port.getValue(api);
		return ok(views.html.portfolio.render(port)); 
	}
	

	
	

}