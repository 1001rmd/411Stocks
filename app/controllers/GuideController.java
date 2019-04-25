package controllers;

import play.mvc.*;
import views.html.*;

public class GuideController extends Controller
{
    public Result guidePage()
    {
        return ok(views.html.guidePage.render());
    }

}
