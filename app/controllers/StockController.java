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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Scala.asScala;

@Singleton
public class StockController extends Controller{

    private final Form<Stock> form;
    private MessagesApi messagesApi;
    private final List<Stock> stocks;

    private final Logger logger = LogFactory.getLogger(getClass());

    @Inject
    public StockController(FormFactory formFactory, MessagesApi messagesApi){
        this.form = formFactory.form(Stock.class);
        this.messagesApi = messagesApi;
        this.stocks = com.common.collect.Lists.newArrayList(
                new Stock("Apple", "APPL"),
                new Stock("Google", "GOOG")
        );
    }

    public Result index(){
        return ok(views.html.index.render);
    }

    public Result listStocks(Http.Request request)
    {
        return ok(views.html.listStocks.render(asScala(persons), form, request, messagesApi.preferred(request)))
    }

    public Result addStock(Http.Request request)
    {
        final Form<Stock> boundForm = form.bindFormRequest(request);

        if(boundForm.hasErrors()){
            logger.error("error = {}", boundForm.errors());
            return badRequest(views.html.listStocks.render(asScala(stocks), boundForm, request, messagesApi.preferred(request)));
        } else{
            Application data = boundForm.get();
            stocks.add(new Stock(data.getName(), data.getSymbol()));
            return redirect(routes.StockController.listStocks())
                    .flashing("info", "Stock successfully added!");
        }
    }


}