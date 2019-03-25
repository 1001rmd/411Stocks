package models;

import io.ebean.Model;
import play.*;
import play.mvc.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class User extends Model{

	@Id @GeneratedValue
	public long id;
	
	public String name;
	
	public String email;
	
	public String password;
	
	

}