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
public class User extends Model{

	@Id @GeneratedValue
	public long id;
	
	@OneToMany(cascade=CascadeType.ALL) @JsonBackReference
	List<Portfolio> portfolios;
	
	public String name;
	
	public String email;
	
	public String password;
	
	public List<Portfolio> getPortfolios(){
		portfolios = new Finder<>(Portfolio.class).query().where().eq("user", this).findList();
		return portfolios;
	}

}