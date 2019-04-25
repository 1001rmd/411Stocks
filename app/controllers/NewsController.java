package controllers;

import play.mvc.*;
import models.StockNews;
import models.Stock;
import models.StockData;
import models.Portfolio;

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
import play.libs.ws.*;
import static play.libs.Scala.asScala;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;
import java.net.*;
import java.util.*;



@Singleton
public class NewsController extends Controller
{
    private final Form<Stock> form;
    private MessagesApi messagesApi;
    private ArrayList<StockNews> stocks;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private StockDataController data;

    @Inject
    public NewsController(FormFactory formFactory, MessagesApi messagesApi, StockDataController data){
        this.form = formFactory.form(Stock.class);
        this.messagesApi = messagesApi;
        this.data = data;
    }

    public Result newsPage(Http.Request request)
    {
        
        stocks = data.getStockStories();

        return ok(views.html.newsPage.render(asScala(stocks), form, request, messagesApi.preferred(request)));
    }

}
