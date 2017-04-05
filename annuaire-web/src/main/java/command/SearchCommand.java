package command;

import com.annuaire.exceptions.ServiceException;
import command.helpers.SearchHelper;
import com.annuaire.dto.ContactInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.annuaire.service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SearchCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(SearchCommand.class);
    private SearchHelper helper;
    private SearchService service;

    public SearchCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        helper = new SearchHelper(request, response);
        service = new SearchService();
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
                forwardWithMethod("index", "search");
                break;
            default:
                throw new ServletException("Can't process" + method);
        }
    }

    private void showSearchForm() {
        logger.info("Rendering search form");
        setTitle("Search form");
    }

    private void search() throws ServletException{
        logger.info("Processing searching");

        try{
            Map<String, String> searchParams = helper.getSearchParams();
            int offset = helper.getOffset();
            List<ContactInfoDto> result = service.getSearchResult(searchParams, offset);

            request.setAttribute("contactList", result);
            request.setAttribute("searchParams", searchParams);
        } catch (ServiceException e){
            throw new ServletException(e);
        }
    }
}
