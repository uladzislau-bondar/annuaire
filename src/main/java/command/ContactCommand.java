package command;

import dao.AddressDao;
import dao.ContactDao;
import entities.Address;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;


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
        //todo add saving phones

        Contact contact = buildContactFromRequest();
        ContactDao contactDao = new ContactDao();
        Long contactId = contactDao.save(contact);

        Address address = buildAddressFromRequest();
        address.setContactId(contactId);
        AddressDao addressDao = new AddressDao();
        addressDao.save(address);

        logger.info("Saving new contact");
    }

    private void updateContact(Long id) {
        //todo add saving phones

        Contact contact = buildContactFromRequest();
        contact.setId(id);
        ContactDao contactDao = new ContactDao();
        contactDao.update(contact);

        Address address = buildAddressFromRequest();
        address.setContactId(contact.getId());
        AddressDao addressDao = new AddressDao();
        addressDao.save(address);

        logger.info("Updating contact #" + id);
    }

    private Contact buildContactFromRequest() {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        Date dateOfBirth = StringUtils.emptyToDate(request.getParameter("dateOfBirth"));
        String sex = request.getParameter("sex");
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

    private Address buildAddressFromRequest() {
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        int zip = StringUtils.emptyToInt(request.getParameter("zip"));

        Address addr = new Address();
        addr.setCountry(country);
        addr.setCity(city);
        addr.setAddress(address);
        addr.setZip(zip);

        return addr;
    }

}
