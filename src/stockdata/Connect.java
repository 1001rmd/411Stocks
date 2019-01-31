package stockdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 *
 * @author Anant Narayan Gaur
 */
public class Connect {

    private static Gson gson = new Gson();

    private static String getData(URL link) {
        String data = "";
        try {
            URLConnection connect = link.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connect.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                data += inputLine;
            }
            in.close();

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return data;
    }

    public static Company getCompanyData(String Symbol) {
        Company datafor = new Company();
        String data = "";
        try {
            URL companyURL = new URL("https://api.iextrading.com/1.0/stock/" + Symbol + "/company");
            data = getData(companyURL);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        datafor = gson.fromJson(data, Company.class);
        return datafor;
    }
    
    public static ArrayList<StockPrices> getStockPrices(Company name){     
        String data = "";
        ArrayList<StockPrices> returnData = new ArrayList<>();
        try {
            URL companyURL = new URL("https://api.iextrading.com/1.0/stock/" + name.getSymbol() + "/chart/1m");
            data = getData(companyURL);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        returnData = gson.fromJson(data, new TypeToken<ArrayList<StockPrices>>(){}.getType());
        
        return returnData;
    }

}
