package controllers;

import models.*;
import views.html.*;
import java.util.List;
import play.libs.Json;
import static play.libs.Scala.asScala;

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

public class BoardController extends Controller{

	private Form<BoardRequest> form;
	private MessagesApi messagesApi;
	private Finder<Long, Leaderboard> boardFinder = new Finder<>(Leaderboard.class);
	private Finder<Long, User> userFinder = new Finder<>(User.class);
	private List<Leaderboard> boards;
	private long sessionUserID;
	
	@Inject
	public BoardController(FormFactory ff, MessagesApi ma){
		this.form = ff.form(BoardRequest.class);
		this.messagesApi = ma;
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
		
		if(boards == null){
			setBoards();
		}
	
		return ok(leaderboardsAll.render(asScala(boards), form, request, messagesApi.preferred(request)));
	}
	
	public Result addBoard(Request request){
		
		
		//Checks to make sure the user is logged in
		try{
			sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
		Form<BoardRequest> boundForm = form.bindFromRequest(request);
		
		if(boundForm.hasErrors()){
            return badRequest(leaderboardsAll.render(asScala(boards), boundForm, request, messagesApi.preferred(request)));
        } else{
			
            User user = userFinder.byId(sessionUserID);
			BoardRequest newBR = form.bindFromRequest(request).get();
			Leaderboard board = new Leaderboard(newBR.name, newBR.start, user);
			
			boards.add(board);
			board.save();
			return redirect(routes.BoardController.display());
        
		}
		
	}
	
	public Result joinBoard(Long id, Request request){
		
		//get User and Leaderboard
		sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		User user = userFinder.byId(sessionUserID);
		Leaderboard board = boardFinder.byId(id);
		
		//Creates new portfolio
		Portfolio portfolio = new Portfolio(user, board);
		
		portfolio.save();
		board.save();
		
		return ok();
	}

	public Result displayBoard(Long id, Request request){
		
		setBoards();
		Leaderboard board = boardFinder.byId(id);
		
		return ok(leaderboard.render(board, request)); 
	}
	
	public Result getBoards(){
		if(boards == null){
			setBoards();
		}
		return ok(Json.toJson(boards));
	}
	
	
	
	//This is a temporary Method to ensure that at least one leaderboard exists
	private void setBoards(){
		
		boards = boardFinder.all();
		
		if(boards.isEmpty()){
			Leaderboard newB = new Leaderboard("Our Leaderboard", 500.00, null);
			newB.save();
			boards = boardFinder.all();
		}
		
	}
	
	

}