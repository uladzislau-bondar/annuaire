package command;

import dao.AddressDao;
import dao.AttachmentDao;
import dao.ContactDao;
import dao.PhoneDao;
import db.Connector;
import dto.AttachmentDto;
import dto.PhoneDto;
import entities.Address;
import entities.Attachment;
import entities.Contact;
import builders.ContactBuilder;
import entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import properties.UploadPropertyService;
import util.DtoUtils;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);
    private ContactDao contactDao;
    private AddressDao addressDao;
    private PhoneDao phoneDao;
    private AttachmentDao attachmentDao;

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        contactDao = new ContactDao();
        addressDao = new AddressDao();
        phoneDao = new PhoneDao();
        attachmentDao = new AttachmentDao();
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
                } else if (query.containsKey("method")) {
                    if (query.get("method").equals("delete")) {
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
        Contact contact = buildContactFromRequest();
        Long contactId = contactDao.save(contact);

        try {
            buildPhotoFromRequest(contactId);
        } catch (Exception e) {
            logger.error(e);
        }

        processAddressSaving(contactId);
        processPhones(contactId);

        logger.info("Saving new contact");
    }

    private void updateContact(Long id) {
        try {
            buildPhotoFromRequest(id);
        } catch (Exception e) {
            logger.error(e);
        }

        //todo transaction
        Contact contact = buildContactFromRequest();
        contact.setId(id);
        contactDao.update(contact);

        processAddressUpdate(id);
        processPhones(id);

        logger.info("Updating contact #" + id);
    }

    private void deleteContact(Long id) {
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
        Address address = addressDao.getByContactId(contactId);
        request.setAttribute("country", address.getCountry());
        request.setAttribute("city", address.getCity());
        request.setAttribute("address", address.getAddress());
        String zip = StringUtils.intToString(address.getZip());
        request.setAttribute("zip", zip);
    }

    private void fillRequestWithPhones(Long contactId) {
        List<Phone> phones = phoneDao.getByContactId(contactId);
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        for (Phone phone : phones) {
            phoneDtoList.add(DtoUtils.convertToDto(phone));
        }

        request.setAttribute("phones", phoneDtoList);
    }

    private void fillRequestWithAttachments(Long contactId) {
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

    private void processAddressSaving(Long contactId) {
        Address address = buildAddressFromRequest();
        address.setContactId(contactId);
        addressDao.save(address);
    }

    private void processAddressUpdate(Long contactId) {
        Address address = buildAddressFromRequest();
        address.setContactId(contactId);
        addressDao.update(address);
    }

    private void processPhones(Long contactId) {
        List<Phone> addedPhones = buildAddedPhonesFromRequest();
        for (Phone phone : addedPhones) {
            phone.setContactId(contactId);
            phoneDao.save(phone);
        }

        List<Phone> updatedPhones = buildUpdatedPhonesFromRequest();
        for (Phone phone : updatedPhones) {
            phone.setContactId(contactId);
            phoneDao.update(phone);
        }

        List<Long> deletedPhonesIds = buildDeletedPhonesIdsFromRequest();
        for (Long id : deletedPhonesIds) {
            phoneDao.delete(id);
        }

    }

    private List<Phone> buildAddedPhonesFromRequest() {
        String addedPhonesJson = request.getParameter("phonesToAdd");
        JSONArray addedPhones = new JSONArray(addedPhonesJson);
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < addedPhones.length(); i++) {
            JSONObject object = addedPhones.getJSONObject(i);
            Phone phone = new Phone();
            phone.setCountryCode(Integer.valueOf(object.getString("countryCode")));
            phone.setNumber(Integer.valueOf(object.getString("number")));
            phone.setType(object.getString("type"));
            phone.setComment(object.getString("comment"));

            phones.add(phone);
        }

        return phones;
    }

    private List<Phone> buildUpdatedPhonesFromRequest() {
        String updatedPhonesJson = request.getParameter("phonesToUpdate");

        JSONArray updatedPhones = new JSONArray(updatedPhonesJson);
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < updatedPhones.length(); i++) {
            JSONObject object = updatedPhones.getJSONObject(i);
            Phone phone = new Phone();
            phone.setId(Long.valueOf(object.getString("id")));
            phone.setCountryCode(Integer.valueOf(object.getString("countryCode")));
            phone.setNumber(Integer.valueOf(object.getString("number")));
            phone.setType(object.getString("type"));
            phone.setComment(object.getString("comment"));

            phones.add(phone);
        }

        return phones;
    }

    private List<Long> buildDeletedPhonesIdsFromRequest() {
        return StringUtils.stringArrayToListOfLongs(request.getParameterValues("phoneToDelete"));
    }

    // todo uploading doesn't work
    private void buildPhotoFromRequest(Long contactId) throws ServletException, IOException {
        Part photo = request.getPart("photo");

        String uploadFilePath = UploadPropertyService.getInstance().getPath() + File.separator + "/contact" + contactId;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        String fileName = Paths.get(photo.getSubmittedFileName()).getFileName().toString();
        logger.info(fileName);
        photo.write(uploadFilePath + File.separator + fileName);
    }

}
