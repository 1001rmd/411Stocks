package stockdata;

import java.util.ArrayList;
import java.util.Scanner;

public class StockData {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String name = "";
        System.out.print("Please Enter company symbol: ");
        name = reader.nextLine();
        
        Company data = Connect.getCompanyData(name);        
        System.out.println(data.toString());
        
        ArrayList<StockPrices> companyStocks = Connect.getStockPrices(data);

        for (StockPrices st : companyStocks) {
            System.out.println(st.toString());
        }
    }

}
