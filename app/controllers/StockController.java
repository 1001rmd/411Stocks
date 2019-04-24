package controllers;

import play.mvc.*;
import models.Stock;
import models.StockData;
import models.Portfolio;
import models.BuyRequest;

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

import static play.libs.Scala.asScala;

@Singleton
public class StockController extends Controller{

    private final Form<BuyRequest> form;
    //public Form <listStock> forms;
    private MessagesApi messagesApi;
    private ArrayList<StockData> stocks;
    private final Logger logger = LoggerFactory.getLogger(getClass());
	private StockDataController data;
	
	//these variables exist so the controller can remember where to buy and sell stock
	private Portfolio portfolio;
	private String stockSymbol;
	

    @Inject
    public StockController(FormFactory formFactory, MessagesApi messagesApi, StockDataController data){
        
		this.form = formFactory.form(BuyRequest.class);
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
	
	
	public Result shopStock(Long portId, String symbol){
		
		
		Portfolio portfolio = new Finder<>(Portfolio.class).byId(portId);
		portfolio.getValue(data);
		this.portfolio = portfolio;
		
		StockData stock = data.getStock(symbol);
		stockSymbol = symbol;
		
		return ok(views.html.buyStock.render(portfolio, stock, form));
		
	}

    public Result buyStock(Request request){
		
		BuyRequest buyRequest = form.bindFromRequest(request).get();
		
		try{
		
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
			
			return redirect(routes.PortfolioController.displayPortfolio(id));
			
		
		}catch(Exception e){}
		
		return redirect(routes.StockController.shopStock(portfolio.id, stockSymbol));
		
	}
	
	public Result sellStock(long id, String symbol, double quantity){
		
		return ok();
	}

    

}
