package controllers;

import play.mvc.*;
import models.Stock;
import play.data.*;
import views.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.math.BigDecimal;
import play.libs.Json.*;

import static play.libs.Scala.asScala;

@Singleton
public class StockController extends Controller{

    private final Form<Stock> form;
    //public Form <listStock> forms;
    private MessagesApi messagesApi;
    private final ArrayList<Stock> stocks;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public StockController(FormFactory formFactory, MessagesApi messagesApi){
        this.form = formFactory.form(Stock.class);
        this.messagesApi = messagesApi;
        
		this.stocks = new ArrayList<Stock>();
        //stocks.add(new Stock("APPL", "Apple"));
        //stocks.add(new Stock("GOOG", "Google"));
        
    }


    public Result listStocks(Http.Request request)
    {
        return ok(views.html.listStocks.render(asScala(stocks), form, request, messagesApi.preferred(request)));
    }

    public Result addStock(Http.Request request) throws IOException
    {
        final Form<Stock> boundForm = form.bindFromRequest(request);

        //listStock listStocks = forms.bindFromRequest(request).get();

        if(boundForm.hasErrors()){
            logger.error("error = {}", boundForm.errors());
            return badRequest(views.html.listStocks.render(asScala(stocks), boundForm, request, messagesApi.preferred(request)));
        } else{
            Stock data = boundForm.get();
            String symbolInput = "amzn";
            //String descriptionInput = data.getDescription;
            String description = getStockDetails(symbolInput);
            data.setDescription(description);
            //stocks.add(new Stock(data.getSymbol, data.getDescription));
            return redirect(routes.StockController.listStocks())
                    .flashing("info", "Stock successfully added!");
        }
    }

    public String getStockDetails(String symbol) throws IOException
    {
        String APIurl = "https://api.iextrading.com/1.0/stock/" + symbol + "/company";
        String charset = "UTF-8";

        URLConnection connection = new URL(APIurl).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();
        String textValue = new String();
        Boolean lineEnd = false;
        Boolean lineStart = false;
        ArrayList<String> list = new ArrayList<String>();
        int charValue = 0;
        while (charValue != -1) {
            charValue = response.read();
            if ((char) charValue == '{') {
                lineStart = true;
                lineEnd = false;
            } else if ((char) charValue == '}') {
                lineEnd = true;
                lineStart = false;
            }

            if (lineStart) {
                textValue += (char) charValue;
            } else if (lineEnd) {
                textValue += (char) charValue;
                list.add(textValue);
                textValue = "";
                lineEnd = false;
            }
        }
        String symbolz = "test";
        String descriptionz = "test";

        Stock stock = new Stock(symbolz, descriptionz);
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject(list.get(i));
            stock.setSymbol(obj.getString("symbol"));
            stock.setCompanyName(obj.getString("companyName"));
            stock.setExchange(obj.getString("exchange"));
            stock.setIndustry(obj.getString("industry"));
            stock.setDescription(obj.getString("description"));
            //String symbols = stock.setSymbol(obj.getString("symbol"));
            //String description = stock.setDescription(obj.getString("description"));
            String descriptions = stock.getDescription();
            stock.setCEO(obj.getString("CEO"));
        }
        String descriptions = stock.getDescription();
        //return stock;
        return descriptions;
    }

}
