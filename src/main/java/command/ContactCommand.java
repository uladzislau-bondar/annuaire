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

        request.setAttribute("id", id);

        setTitle(contact.getFirstName());

        logger.info("Show form for editing contact #" + id);
    }

    private void saveContact() {
        //todo add saving address
        //todo add saving phones

        Contact contact = buildContactFromRequest();
        ContactDao contactDao = new ContactDao();
        contactDao.save(contact);

        logger.info("Saving new contact");
    }

    private void updateContact(Long id) {
        //todo add saving address
        //todo add saving phones

        Contact contact = buildContactFromRequest();
        contact.setId(id);
        ContactDao contactDao = new ContactDao();
        contactDao.update(contact);

        logger.info("Updating contact #" + id);
    }

    private Contact buildContactFromRequest(){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        String sex = request.getParameter("middleName");
        String citizenship = request.getParameter("citizenship");
        String maritalStatus = request.getParameter("maritalStatus");
        String website = request.getParameter("website");
        String email = request.getParameter("email");
        String placeOfWork = request.getParameter("placeOfWork");

        ContactBuilder builder = new ContactBuilder();
        builder.firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .dateOfBirth(dateOfBirth)
                .sex(sex)
                .citizenship(citizenship)
                .maritalStatus(maritalStatus)
                .webSite(website)
                .email(email)
                .placeOfWork(placeOfWork);

        return builder.build();
    }


}
