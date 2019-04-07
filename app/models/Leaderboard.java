package models;

import io.ebean.Model;
import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Leaderboard extends Model{

	@Id @GeneratedValue
	public long id;
	
	public String name;
	
	@OneToMany @JsonBackReference
	public List<Portfolio> portfolios;
	
	@ManyToOne
	public User owner;
	
	public double startingAccount;
	
	
	public Leaderboard(String name, double startingAccount, User user){
		
		this.name = name;
		this.startingAccount = startingAccount;
		portfolios = new ArrayList<Portfolio>();
		owner = user;
		
	}
	
	public List<Portfolio> getPortfolios(){
		
		if(portfolios == null){
			//portfolios = new Finder<>(Portfolio.class).query().where().eq("leaderboard", id).findList();
			portfolios = new Finder<>(Portfolio.class).all();
		}
		
		return portfolios;
	}

}