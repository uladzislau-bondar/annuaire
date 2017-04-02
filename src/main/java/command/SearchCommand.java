package command;

import command.helpers.SearchHelper;
import dto.ContactInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.SearchService;

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
        helper = new SearchHelper(request);
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
        }
    }

    private void showSearchForm() {
        logger.info("Rendering search form");
        setTitle("Search form");
    }

    private void search() {
        logger.info("Processing searching");

        Map<String, String> searchParams = helper.getSearchParams();
        int offset = helper.getOffset();
        List<ContactInfoDto> result = service.getSearchResult(searchParams, offset);

        request.setAttribute("contactList", result);
        request.setAttribute("searchParams", searchParams);
    }
}
