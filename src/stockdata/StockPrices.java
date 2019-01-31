
package stockdata;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Anant Narayan Gaur
 */
public class StockPrices {
    
    private Date date;
    private float open;
    private float high;
    private float low;
    private float close;
    private float volume;
    private float change;
    private float changePercent;
    private float vwap;
    private String label;
    private float changeOverTime;

    @Override
    public String toString() {
        return "StockPrices{" + "date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", change=" + change + ", changePercent=" + changePercent + ", vwap=" + vwap + ", label=" + label + ", changeOverTime=" + changeOverTime + '}';
    }

    public StockPrices(Date date, float open, float high, float low, float close, float volume, float change, float changePercent, float vwap, String label, float changeOverTime) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.change = change;
        this.changePercent = changePercent;
        this.vwap = vwap;
        this.label = label;
        this.changeOverTime = changeOverTime;
    }

    public StockPrices() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(float changePercent) {
        this.changePercent = changePercent;
    }

    public float getVwap() {
        return vwap;
    }

    public void setVwap(float vwap) {
        this.vwap = vwap;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getChangeOverTime() {
        return changeOverTime;
    }

    public void setChangeOverTime(float changeOverTime) {
        this.changeOverTime = changeOverTime;
    }
    
    public ObservableList getListView(){
        String a = "Open: " + open;
        String b = "High: " + high;
        String c = "Close: " + close;
        String d = "Change: " + changePercent + "%";
        ObservableList list = FXCollections.observableArrayList();
        list.addAll(a, b, c, d);
        
        return  list;
    }
    
    
}
