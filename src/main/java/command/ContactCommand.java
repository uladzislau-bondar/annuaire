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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import properties.UploadPropertyService;
import service.ShowContactService;
import service.UpdateContactService;
import builders.UpdateContactServiceBuilder;
import util.DtoUtils;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);
    private ContactDao contactDao;
    private AddressDao addressDao;
    private PhoneDao phoneDao;
    private AttachmentDao attachmentDao;

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
        ShowContactService service = new ShowContactService();
        Contact contact = service.getContactById(id);
        fillRequestWithData(contact);
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
            Contact contact = buildContactFromRequest();
            contact.setId(id);
            Address address = buildAddressFromRequest();
            List<Phone> addedPhones = buildAddedPhonesFromRequest();
            List<Phone> updatedPhones = buildUpdatedPhonesFromRequest();
            List<Long> deletedPhonesIds = buildDeletedPhonesIdsFromRequest();
            List<Attachment> addedAttachments = buildAddedAttachmentsFromRequest();
            List<Attachment> updatedAttachments = buildUpdatedAttachmentsFromRequest();
            List<Long> deletedAttachmentsIds = buildDeletedAttachmentsIdsFromRequest();

            UpdateContactServiceBuilder builder = new UpdateContactServiceBuilder()
                    .contact(contact)
                    .address(address)
                    .addedPhones(addedPhones)
                    .updatedPhones(updatedPhones)
                    .deletedPhonesIds(deletedPhonesIds)
                    .addedAttachments(addedAttachments)
                    .updatedAttachments(updatedAttachments)
                    .deletedAttachmentsIds(deletedAttachmentsIds);

            UpdateContactService service = builder.build();
            service.update();
        } catch (Exception e) {
            logger.error(e);
        }

        logger.info("Updating contact #" + id);
    }

    private void deleteContact(Long id) {
        contactDao.delete(id);

        logger.info("Deleting contact #" + id);
    }

    private void fillRequestWithData(Contact contact) {
        fillRequestWithContact(contact);
        fillRequestWithAddress(contact.getAddress());
        fillRequestWithPhones(contact.getPhones());
        fillRequestWithAttachments(contact.getAttachments());
    }

    private void fillRequestWithContact(Contact contact) {
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

    private void fillRequestWithAddress(Address address) {
        request.setAttribute("country", address.getCountry());
        request.setAttribute("city", address.getCity());
        request.setAttribute("address", address.getAddress());
        String zip = StringUtils.intToString(address.getZip());
        request.setAttribute("zip", zip);
    }

    private void fillRequestWithPhones(List<Phone> phones) {
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        for (Phone phone : phones) {
            phoneDtoList.add(DtoUtils.convertToDto(phone));
        }

        request.setAttribute("phones", phoneDtoList);
    }

    private void fillRequestWithAttachments(List<Attachment> attachments) {
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

    private void processAttachments(Long contactId) throws ServletException, IOException{
        List<Attachment> addedAttachments = buildAddedAttachmentsFromRequest();
        for (Attachment attachment : addedAttachments) {
            attachment.setContactId(contactId);
            attachmentDao.save(attachment);
        }

        List<Attachment> updatedAttachments = buildUpdatedAttachmentsFromRequest();
        for (Attachment attachment : updatedAttachments) {
            attachment.setContactId(contactId);
            attachmentDao.update(attachment);
        }

        List<Long> deletedAttachmentsIds = buildDeletedAttachmentsIdsFromRequest();
        for (Long id : deletedAttachmentsIds) {
            attachmentDao.delete(id);
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


    private void buildPhotoFromRequest(Long contactId) throws ServletException, IOException {
        Part photo = request.getPart("photo");

        String uploadFilePath = UploadPropertyService.getInstance().getPath() + File.separator + "/contact" + contactId;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String name = "profilePic";
        logger.info(name);
        photo.write(uploadFilePath + File.separator + name);
    }

    private List<Attachment> buildAddedAttachmentsFromRequest() throws ServletException, IOException{
        List<Part> added = request.getParts().stream().filter(part -> "addedAttachment".equals(part.getName())).collect(Collectors.toList());
        List<Attachment> attachments = new ArrayList<>();
        for (Part filePart : added) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            logger.info(fileName);
        }
        return new ArrayList<>();
    }

    private List<Attachment> buildUpdatedAttachmentsFromRequest() throws ServletException, IOException{
        List<Part> updated = request.getParts().stream().filter(part -> "updatedAttachment".equals(part.getName())).collect(Collectors.toList());
        for (Part filePart : updated) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            logger.info(fileName);
        }

        return new ArrayList<>();
    }

    private List<Long> buildDeletedAttachmentsIdsFromRequest() {
        return StringUtils.stringArrayToListOfLongs(request.getParameterValues("attachmentToDelete"));
    }

}
