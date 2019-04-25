package controllers;

import play.mvc.*;
import models.Stock;
import models.StockData;
import models.Portfolio;
import models.BuyRequest;
import models.SellRequest;

import io.ebean.Finder;
import play.data.*;
import views.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;
import play.mvc.Http.Request;
import org.json.JSONObject;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.math.BigDecimal;
import play.libs.Json.*;
import play.libs.ws.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import org.json.JSONArray;

import static play.libs.Scala.asScala;

@Singleton
public class StockController extends Controller{

    private final Form<BuyRequest> buyForm;
	private final Form<SellRequest> sellForm;
    private MessagesApi messagesApi;
    private ArrayList<StockData> stocks;
    private final Logger logger = LoggerFactory.getLogger(getClass());
	private StockDataController data;
	
	//these variables exist so the controller can remember where to buy and sell stock
	private Portfolio portfolio;
	private String stockSymbol;
	

    @Inject
    public StockController(FormFactory formFactory, MessagesApi messagesApi, StockDataController data){
        
		this.buyForm = formFactory.form(BuyRequest.class);
		this.sellForm = formFactory.form(SellRequest.class);
        this.messagesApi = messagesApi;
		this.data = data;

    }


    public Result listStocks(Http.Request request, String category){
		
		//Tests for a correct category
		String[] categories = {"infocus ", "mostactive ", "gainers ", "losers "};
		boolean catError = true;
		for(String type : categories){
			if(category.equals(type)){
				catError = false;
			}
		}
		if(catError){
			category = "infocus";
		}
		
		//Query the API
		stocks = data.getStockList(category);
		
        return ok(views.html.listStocks.render(asScala(stocks), category.toUpperCase()));
		
    }
	
	
	public Result displayPortfolio(long id, Request request){
		
		try{
			long sessionUserID = Long.parseLong(request.session().getOptional("userID").get());
		}catch(Exception e){
			//This error means the user is not currently logged in
			//routes the user to login page
			return redirect(routes.LoginController.display(false));
		}
		
		
		Portfolio port = new Finder<>(Portfolio.class).byId(id);
		port.getValue(data);
		portfolio = port;
		return ok(views.html.portfolio.render(port, sellForm)); 
	}
	
	public Result shopStock(Long portId, String symbol){
		
		
		Portfolio portfolio = new Finder<>(Portfolio.class).byId(portId);
		portfolio.getValue(data);
		portfolio.save();
		this.portfolio = portfolio;
		
		StockData stock = data.getStock(symbol);
		stockSymbol = symbol;
		
		return ok(views.html.buyStock.render(portfolio, stock, buyForm));
		
	}

    public Result buyStock(Request request){

		
		try{
			
			BuyRequest buyRequest = buyForm.bindFromRequest(request).get();
		
			//Gets prospective stock
			double quantity = Double.parseDouble(buyRequest.quantity);
			Stock stock = new Stock(stockSymbol, quantity, portfolio);
			
			//Checks for adequite account balance
			if(portfolio.account < stock.getValue(data)){
				throw new Exception();
			}
			
			//Pays for stock
			portfolio.account = portfolio.account - stock.getValue(data);
			
			//Checks to see if stock is already in portfolio
			boolean found = false;
			for(Stock s: portfolio.stocks){
				
				if(s.symbol.equals(stock.symbol)){
					found = true;
					s.quantity += stock.quantity;
					s.save();
					break;
				}
				
			}
			if(!found){
				stock.save();
			}
			
			portfolio.save();
			
			
			//clears the saved variables for the next purchase
			long id = portfolio.id;
			portfolio = null;
			stockSymbol = "";
			
			return redirect(routes.StockController.displayPortfolio(id));
			
		
		}catch(Exception e){}
		
		return redirect(routes.StockController.shopStock(portfolio.id, stockSymbol));
		
	}
	
	
	public Result sellStock(Request request){
		
		
		try{
			
			SellRequest sellRequest = sellForm.bindFromRequest(request).get();
			
			//Validates that quatity
			double quantity = Double.parseDouble(sellRequest.quantity);
			if(quantity < 1){
				throw new Exception();
			}
			
			//Check if user owns stock
			for(Stock s: portfolio.stocks){
				
				if(s.symbol.equals(sellRequest.symbol)){
					if(s.quantity < quantity){
						throw new Exception();
					}
					
					//Get value of stock
					double value = data.getStockValue(s.symbol) * quantity;
					
					//Subtract Stock quantity and add value to account
					portfolio.account += value;
					s.quantity -= quantity;
					s.save();
					portfolio.getValue(data);
					portfolio.save();
					
					if(s.quantity == 0){
						s.delete();
					}
					
					//Clears the saved variables for the next transation
					long id = portfolio.id;
					portfolio = null;
					stockSymbol = "";
					
					return redirect(routes.StockController.displayPortfolio(id));
				}
				
			}
			
		
		}catch(Exception e){}
		
		return redirect(routes.StockController.displayPortfolio(portfolio.id));
	}

    

}
