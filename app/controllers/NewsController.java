package controllers;

import play.mvc.*;
import views.html.*;

public class NewsController extends Controller
{
    public Result newsPage()
    {
        return redirect("https://www.google.com/search?q=stocks+news+today&rlz=1C1CHZL_enUS705US705&source=lnms&tbm=nws&sa=X&ved=0ahUKEwjUrrG5ldXhAhUi1lkKHVOXC3kQ_AUIDigB&biw=1920&bih=937");
    }

}
