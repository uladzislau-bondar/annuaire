package command;

import dao.AddressDao;
import dao.AttachmentDao;
import dao.ContactDao;
import dao.PhoneDao;
import dto.AttachmentDto;
import dto.PhoneDto;
import entities.Address;
import entities.Attachment;
import entities.Contact;
import builders.ContactBuilder;
import entities.Phone;
import enums.PhoneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DtoUtils;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        Map<String, String> query = StringUtils.splitQuery(request.getQueryString());

        switch (method) {
            case "GET":
                if (query.isEmpty()) {
                    showCreationForm();

                    forward("contact");
                } else if (query.containsKey("method")){
                    if (query.get("method").equals("delete")){
                        Long contactId = Long.valueOf(query.get("id"));
                        deleteContact(contactId);
                    }

                    redirect("/");
                } else if (query.containsKey("id")) {
                    Long contactId = Long.valueOf(query.get("id"));
                    showContact(contactId);

                    forward("contact");
                }

                break;
            case "POST":
                if (query.isEmpty()) {
                    saveContact();
                } else if (query.containsKey("id")) {
                    Long contactId = Long.valueOf(query.get("id"));
                    updateContact(contactId);
                }

                redirect("/");
                break;
            default:
                forward("error");
        }


    }

    private void showCreationForm() {
        setTitle("Create new contact");

        logger.info("Show form for creating new contact");
    }

    private void showContact(Long id) {
        fillRequestWithData(id);
        setTitle("Showing contact");

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

        List<Phone> phones = buildPhonesFromRequest();
        PhoneDao phoneDao = new PhoneDao();
        for (Phone phone: phones){
            phone.setContactId(contactId);
            phoneDao.save(phone);
        }

        logger.info("Saving new contact");
    }

    private void updateContact(Long id) {
        Contact contact = buildContactFromRequest();
        contact.setId(id);
        ContactDao contactDao = new ContactDao();
        contactDao.update(contact);

        Address address = buildAddressFromRequest();
        address.setContactId(contact.getId());
        AddressDao addressDao = new AddressDao();
        addressDao.update(address);

        List<Phone> phones = buildPhonesFromRequest();
        PhoneDao phoneDao = new PhoneDao();
        for (Phone phone: phones){
            phone.setContactId(contact.getId());
            phoneDao.save(phone);
        }

        logger.info("Updating contact #" + id);
    }

    private void deleteContact(Long id) {
        ContactDao contactDao = new ContactDao();
        contactDao.delete(id);

        logger.info("Deleting contact #" + id);
    }

    private void fillRequestWithData(Long id) {
        fillRequestWithContact(id);
        fillRequestWithAddress(id);
        fillRequestWithPhones(id);
        fillRequestWithAttachments(id);
    }

    private void fillRequestWithContact(Long id) {
        ContactDao contactDao = new ContactDao();
        Contact contact = contactDao.getById(id);
        request.setAttribute("id", contact.getId());
        request.setAttribute("firstName", contact.getFirstName());
        request.setAttribute("lastName", contact.getLastName());
        request.setAttribute("middleName", contact.getMiddleName());
        request.setAttribute("dateOfBirth", contact.getDateOfBirth());
        request.setAttribute("sex", contact.getSex().value());
        request.setAttribute("citizenship", contact.getCitizenship());
        request.setAttribute("maritalStatus", contact.getMaritalStatus());
        request.setAttribute("website", contact.getWebsite());
        request.setAttribute("email", contact.getEmail());
        request.setAttribute("placeOfWork", contact.getPlaceOfWork());
    }

    private void fillRequestWithAddress(Long contactId) {
        AddressDao addressDao = new AddressDao();
        Address address = addressDao.getByContactId(contactId);
        request.setAttribute("country", address.getCountry());
        request.setAttribute("city", address.getCity());
        request.setAttribute("address", address.getAddress());
        String zip = StringUtils.intToString(address.getZip());
        request.setAttribute("zip", zip);
    }

    private void fillRequestWithPhones(Long contactId) {
        PhoneDao phoneDao = new PhoneDao();
        List<Phone> phones = phoneDao.getByContactId(contactId);
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        for (Phone phone : phones) {
            phoneDtoList.add(DtoUtils.convertToDto(phone));
        }

        request.setAttribute("phones", phoneDtoList);
    }

    private void fillRequestWithAttachments(Long contactId) {
        AttachmentDao attachmentDao = new AttachmentDao();
        List<Attachment> attachments = attachmentDao.getByContactId(contactId);
        List<AttachmentDto> attachmentDtoList = new ArrayList<>();
        for (Attachment attachment : attachments) {
            attachmentDtoList.add(DtoUtils.convertToDto(attachment));
        }

        request.setAttribute("attachments", attachmentDtoList);
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
                .website(website)
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

    private List<Phone> buildPhonesFromRequest(){
        List<Integer> countryCodes = StringUtils.stringArrayToListOfIntegers(request.getParameterValues("countryCode"));
        List<Integer> numbers = StringUtils.stringArrayToListOfIntegers(request.getParameterValues("number"));
        List<String> phoneTypes = StringUtils.stringArrayToListOfStrings(request.getParameterValues("type"));
        List<String> comments = StringUtils.stringArrayToListOfStrings(request.getParameterValues("comment"));

        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i< countryCodes.size(); i++){
            Phone phone = new Phone();
            phone.setCountryCode(countryCodes.get(i));
            phone.setNumber(numbers.get(i));
            phone.setType(phoneTypes.get(i));
            phone.setComment(comments.get(i));

            phones.add(phone);
        }

        return phones;
    }

}
