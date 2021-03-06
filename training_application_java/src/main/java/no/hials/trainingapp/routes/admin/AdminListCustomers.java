package no.hials.trainingapp.routes.admin;

import java.sql.SQLException;
import java.util.List;
import no.hials.trainingapp.datasource.DataItem;
import no.hials.trainingapp.datasource.DataSource;
import no.hials.trainingapp.routing.TemplateRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 *
 */
public class AdminListCustomers extends TemplateRoute {

    public AdminListCustomers(DataSource datasource, Request req, Response resp) {
        super(datasource, req, resp);
    }

    @Override
    public ModelAndView handle() throws SQLException {
       
        List<DataItem> customers = getDataSource().getAllCustomers(100);

        setData("customers", customers);

        return renderTemplate("admin/list-customers");
        
    }

}
