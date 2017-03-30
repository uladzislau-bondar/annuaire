package command;

import command.helpers.SearchHelper;
import dao.ContactDao;
import db.ConnectionPool;
import dto.ContactInfoDto;
import entities.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.SearchService;
import util.DtoUtils;
import util.MyStringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
                forward("index");
                break;
        }
    }

    private void showSearchForm() {
        logger.info("Rendering search form");
        setTitle("Search form");
    }

    // todo process pagination on search page
    private void search() {
        logger.info("Processing searching");

        Map<String, String> searchParams = helper.getSearchParams();
        List<ContactInfoDto> result = service.getSearchResult(searchParams);

        request.setAttribute("contactList", result);
    }
}
