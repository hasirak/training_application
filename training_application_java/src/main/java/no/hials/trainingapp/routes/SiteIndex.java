package no.hials.trainingapp.routes;

import no.hials.trainingapp.datasource.DataSource;
import no.hials.trainingapp.routing.TemplateRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Handles the site index
 *
 * @author Per Myren <progrper@gmail.com>
 */
public class SiteIndex extends TemplateRoute
{

    public SiteIndex(DataSource ds, Request req, Response resp)
    {
        super(ds, req, resp);
    }

    @Override
    public ModelAndView handle()
    {
        return renderTemplate("index");
    }

}