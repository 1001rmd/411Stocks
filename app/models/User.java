package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.List;

@Entity
public class User extends Model{

	@Id @GeneratedValue
	public long id;
	
	@OneToMany
	List<Portfolio> porfolios;
	
	public String name;
	
	public String email;
	
	public String password;
	
	

}