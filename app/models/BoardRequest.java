package models;


public class BoardRequest{

	public String name;
	
	public double start;

	
	public void setName(String name){
		this.name = name;
	}
	
	public void setStart(String start){
		
		try{
			this.start = Double.parseDouble(start);
		}catch(Exception e){
			this.start = 500.0; //If we can't read a double, sets to default value
		}
		
	}
	
	public String getName(){
		return name;
	}
	
	public double getStart(){
		return start;
	}
	
}