package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;


public class SearchCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(SearchCommand.class);

    public SearchCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException{
        String method = request.getMethod();

        switch (method){
            case "GET":
                showSearchForm();

                forward("search");
                break;
            case "POST":
                search();

                //todo redirect to index page
                break;
            default:
                forward("error");
                break;
        }
    }

    private void showSearchForm() {
        setTitle("Search form");

        logger.info("Rendering search form");
    }

    private void search() {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        Date dateOfBirth = StringUtils.emptyToDate(request.getParameter("dateOfBirth"));
        String sex = request.getParameter("sex");
        String citizenship = request.getParameter("citizenship");
        String maritalStatus = request.getParameter("maritalStatus");

        //todo process retrieving contacts from db and redirecting to index page
    }
}
