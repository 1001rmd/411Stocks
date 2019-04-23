package controllers;

import play.mvc.*;
import models.Stock;
import models.StockData;
import models.Portfolio;

import play.data.*;
import views.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

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

    private final Form<Stock> form;
    //public Form <listStock> forms;
    private MessagesApi messagesApi;
    private ArrayList<StockData> stocks;
    private final Logger logger = LoggerFactory.getLogger(getClass());
	private StockDataController data;

    @Inject
    public StockController(FormFactory formFactory, MessagesApi messagesApi, StockDataController data){
        
		this.form = formFactory.form(Stock.class);
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
		
        return ok(views.html.listStocks.render(asScala(stocks), category.toUpperCase(), form, request, messagesApi.preferred(request)));
		
    }
	
	public Result buyStock(Portfolio portfolio){
		
		return ok();
		
	}

    public Result addStock(Http.Request request) throws IOException
    {
        /*final Form<Stock> boundForm = form.bindFromRequest(request);

        //listStock listStocks = forms.bindFromRequest(request).get();

        if(boundForm.hasErrors()){
            logger.error("error = {}", boundForm.errors());
            return badRequest(views.html.listStocks.render(asScala(stocks), boundForm, request, messagesApi.preferred(request)));
        } else{
            Stock data = boundForm.get();
            String symbolInput = "amzn";
            //String descriptionInput = data.getDescription;
            String description = getStockDetails(symbolInput);
            data.setDescription(description);
            //stocks.add(new Stock(data.getSymbol, data.getDescription));
            return redirect(routes.StockController.listStocks("infocus"))
                    .flashing("info", "Stock successfully added!");
        }*/
		
		return ok();
    }

    

}
