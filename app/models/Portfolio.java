package models;

import java.util.LinkedList;
import java.util.ArrayList;

import io.ebean.Model;
import javax.persistence.Entity;
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
	
	public ArrayList<Stock> stocks;
	
	public LinkedList<String> history;
	
	public double value;
	
	double account;

	public Portfolio(User user, Leaderboard board){
		
		this.user = user;
		leaderboard = board;
		account = board.startingAccount;
		
		stocks = new ArrayList<Stock>();
		history = new LinkedList<String>();
		
	}
	
	public double getValue(){
		/*TODO 
		* This method will query the api to find the current value
		* of the portfolio and update the value variable
		*/
		value = account; //+ value of stocks
		return value;
	}


} 
