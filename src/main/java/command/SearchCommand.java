package command;

import dao.ContactDao;
import db.ConnectionPool;
import dto.ContactInfoDto;
import entities.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

                forward("index");
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
//        String middleName = request.getParameter("middleName");
//        Date dateOfBirth = MyStringUtils.emptyToDate(request.getParameter("dateOfBirth"));
//        String sex = request.getParameter("sex");
//        String citizenship = request.getParameter("citizenship");
//        String maritalStatus = request.getParameter("maritalStatus");
//        String country = request.getParameter("country");
//        String city = request.getParameter("city");
//        String address = request.getParameter("address");
//        int zip = Integer.valueOf(request.getParameter("zip"));

        Map<String, String> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        try{
            ContactDao dao = new ContactDao(ConnectionPool.getConnection());
            List<Contact> contacts = dao.getBy(params);
            List<ContactInfoDto> dtos = new ArrayList<>();
            for (Contact contact : contacts) {
                dtos.add(DtoUtils.convertToInfoDto(contact));
            }

            request.setAttribute("contactList", dtos);
        } catch (SQLException e){

        }
    }
}
