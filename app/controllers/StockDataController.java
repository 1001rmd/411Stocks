package controllers;

import models.StockData;
import java.util.ArrayList;

import javax.inject.Inject;
import play.*;
import play.mvc.*;
import play.mvc.Http.Request;
import play.libs.ws.*;
import play.libs.Json;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;



public class StockDataController extends Controller{


	private final WSClient ws;


	@Inject
	public StockDataController(WSClient ws){
		this.ws = ws;
	}
	
	
	public ArrayList<StockData> getStockList(String category){
		
		//API Call and return
		try{
			WSRequest request = ws.url("https://api.iextrading.com/1.0/stock/market/list/" + category);
			WSResponse response = request.get().toCompletableFuture().get();
			
			Type listType = new TypeToken<ArrayList<StockData>>(){}.getType();
			return new Gson().fromJson(response.getBody(), listType);

			
		}catch(Exception e){
			return null;
		}
	}
	
	public StockData getStock(String symbol){
		
		try{
			WSRequest request = ws.url("https://api.iextrading.com/1.0/stock/" + symbol +"/quote");
			WSResponse response = request.get().toCompletableFuture().get();
			
			StockData stock = new Gson().fromJson(response.getBody(), StockData.class);
			
			return stock;
			
		}catch(Exception e){
			return null;
		}
	}
	
	
	public double getStockValue(String symbol){
		
		try{
			WSRequest request = ws.url("https://api.iextrading.com/1.0/stock/" + symbol +"/price");
			WSResponse response = request.get().toCompletableFuture().get();
		
			return Double.parseDouble(response.getBody());
			
		}catch(Exception e){
			return 0.0;
		}
	}
	
	public ArrayList<StockNews> getStockStories(){
		try{
			WSRequest request = ws.url("https://api.iextrading.com/1.0/stock/market/news/last/3");
			WSResponse response = request.get().toCompletableFuture().get();

			Type listType = new TypeToken<ArrayList<StockNews>>(){}.getType();

			return new Gson().fromJson(response.getBody(), listType);


		}catch(Exception e){
			return null;
		}
	}
	



}
