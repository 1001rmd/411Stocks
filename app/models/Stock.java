package models;

import javax.persistence.*;
import io.ebean.Model;

@Entity
public class Stock{
    public String symbol;
    public String name;
    public long stockId;
    public double askPrice;
    public double bidPrice;
    public String companyName;
    public String exchange;
    public String industry;
    public String description;
    public String CEO;

    public Stock(String symbol, String description)
    {
        this.symbol = symbol;
        this.description = description;
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


    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getExchange() {
        return exchange;
    }
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCEO() {
        return CEO;
    }
    public void setCEO(String cEO) {
        CEO = cEO;
    }
    public long getStockId() {
        return stockId;
    }
    public void setStockId(long stockId) {
        this.stockId = stockId;
    }
    public double getBidPrice() {
        return bidPrice;
    }
    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }
    public double getAskPrice() {
        return askPrice;
    }
    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }
}
