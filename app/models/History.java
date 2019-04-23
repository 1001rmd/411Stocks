package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class History extends Model{

	@Id @GeneratedValue
	public long id;
	
	@ManyToOne @JsonManagedReference
	public Portfolio portfolio;
	
	public String description;
	public double value;


}