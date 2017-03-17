package command;

import dao.ContactDao;
import entities.Contact;
import entities.ContactBuilder;
import enums.Sex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Enumeration;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
        super.forward("contact");
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        String query = request.getQueryString();

        switch (method) {
            case "GET":
                if (query == null) {
                    showCreationForm();
                } else {
                    Long contactId = Long.valueOf(request.getParameter("id"));
                    showContact(contactId);
                }
                break;
            case "POST":
                if (query == null) {
                    saveContact();
                } else {
                    Long contactId = Long.valueOf(request.getParameter("id"));
                    updateContact(contactId);
                }
                break;
            case "DELETE":
                break;
        }


    }

    private void showCreationForm() {
        setTitle("Create new contact");

        logger.info("Show form for creating new contact");
    }

    private void showContact(Long id) {
        ContactDao contactDao = new ContactDao();
        Contact contact = contactDao.getById(id);
        request.setAttribute("firstName", contact.getFirstName());
        request.setAttribute("lastName", contact.getLastName());
        request.setAttribute("middleName", contact.getMiddleName());
        request.setAttribute("dateOfBirth", contact.getDateOfBirth());
        request.setAttribute("sex", contact.getSex().value());
        request.setAttribute("citizenship", contact.getCitizenship());
        request.setAttribute("maritalStatus", contact.getMaritalStatus());
        request.setAttribute("website", contact.getWebSite());
        request.setAttribute("email", contact.getEmail());
        request.setAttribute("placeOfWork", contact.getPlaceOfWork());

        //todo add contact address
        //todo add contact phones

        setTitle(contact.getFirstName());

        logger.info("Show form for editing contact #" + id);
    }

    private void saveContact() {
        ContactBuilder builder = new ContactBuilder();
        String firstName = (String) request.getAttribute("firstName");
        String lastName = (String) request.getAttribute("lastName");
        String middleName = (String) request.getAttribute("middleName");
        Date dateOfBirth = (Date) request.getAttribute("dateOfBirth");
        Sex sex = Sex.valueOf((String) request.getAttribute("middleName"));
        String citizenship = (String) request.getAttribute("citizenship");
        String maritalStatus = (String) request.getAttribute("maritalStatus");
        String website = (String) request.getAttribute("website");
        String email = (String) request.getAttribute("email");
        String placeOfWork = (String) request.getAttribute("placeOfWork");
        Contact contact = builder.build();

        //todo add saving address
        //todo add saving phones

        ContactDao contactDao = new ContactDao();
        contactDao.save(contact);
    }

    private void updateContact(Long id) {
        //todo add updateContact method body
    }

}
