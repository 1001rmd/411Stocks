package models;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import controllers.StockDataController;

import io.ebean.Model;
import io.ebean.Finder;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Portfolio extends Model{

	@Id @GeneratedValue
	public long id;
	
	@ManyToOne @JsonManagedReference
	public User user;
	
	@ManyToOne @JsonManagedReference
	public Leaderboard leaderboard;
	
	@OneToMany
	public List<Stock> stocks;
	
	@Transient
	public List<StockData> stockData;
	
	@OneToMany
	public List<History> history;
	
	public double value;
	
	public double account;

	public Portfolio(User user, Leaderboard board){
		
		this.user = user;
		leaderboard = board;
		account = board.startingAccount;
		
		stocks = new ArrayList<Stock>();
		history = new LinkedList<History>();
		
	}
	
	public List<Stock> getStocks(){
		stocks = new Finder<>(Stock.class).query().where().eq("portfolio", this).findList();
		return stocks;
	}
	
	public List<History> getHistory(){
		history = new Finder<>(History.class).query().where().eq("portfolio", this).findList();
		return history;
	}
	
	public double getValue(StockDataController api){
		
		value = 0.0;
		
		//Ensures lists are inialized
		getStocks();
		if(stocks == null){ stocks = new ArrayList<Stock>();}
		stockData = new ArrayList<StockData>();
		
		//Iterate through stocks
		for(Stock s : stocks){
			value+= s.getValue(api);
			stockData.add(api.getStock(s.symbol));
		}
		
		value += account; //+ value of unspent money
		return value;
	}


} 
