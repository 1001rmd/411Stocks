

package stockdata;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Ryan Dougherty
 */
public class Stock {

   ArrayList<StockPrices> theList;
   
   public Stock(ArrayList<StockPrices> data){
       theList = data;
   }
   
   public StockPrices getPrice(int i){
       
       return theList.get(i);
       
   }
   
    
}
