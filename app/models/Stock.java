package models;

import javax.persistence.*;
import io.ebean.Model;

@Entity
public class Stock{
    public String symbol;
    public String name;

    public Stock(String name, String symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    public String getSymbol(){
        return symbol;
    }

}