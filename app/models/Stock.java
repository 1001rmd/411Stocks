package models;

import controllers.StockDataController;
import javax.persistence.*;
import io.ebean.Model;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Stock extends Model{
    
	@Id @GeneratedValue
	public long id;
	
	public String symbol;
    public double value;
	public double quantity;
	
	@ManyToOne @JsonManagedReference
	public Portfolio portfolio;
	
	public Stock(String symbol, double quantity, Portfolio portfolio){
		this.symbol = symbol;
		this.quantity = quantity;
		this.portfolio = portfolio;
	}
 
	public double getValue(StockDataController api){
		
		value = api.getStockValue(symbol) * quantity;
		this.save();
		return value;
		
	}
	
 
}
