package command;

import dto.AttachmentDto;
import dto.ContactDatabaseDto;
import dto.ContactFrontDto;
import dto.PhoneDto;
import entities.Attachment;
import entities.Contact;
import builders.ContactBuilder;
import entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import properties.UploadPropertyService;
import service.ContactService;
import util.DtoUtils;
import util.MyStringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);
    private ContactService service;

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        service = new ContactService();
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        Map<String, String> query = MyStringUtils.splitQuery(request.getQueryString());

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
        logger.info("Show form for creating new contact");
        setTitle("Contact Creation Form");
    }

    private void showContact(Long id) {
        logger.info("Show form for editing contact #{}", id);
        setTitle("Contact " + id);

        ContactDatabaseDto contact = service.get(id);
        fillRequestWithData(contact);
    }

    private void saveContact() {
        logger.info("Saving new contact");

        try {
            ContactFrontDto contact = buildContactFromRequest();
            service.save(contact);
        } catch (ServletException | IOException e) {
            logger.error(e);
        }
    }

    private void updateContact(Long id) {
        logger.info("Updating contact #{}", id);

        try {
            ContactFrontDto contact = buildContactFromRequest();
            service.update(contact, id);
        } catch (ServletException | IOException e) {
            logger.error(e);
        }
    }

    private void deleteContact(Long id) {
        logger.info("Deleting contact #{}", id);

        service.delete(id);
    }

    private void fillRequestWithData(ContactDatabaseDto dto) {
        fillRequestWithContact(dto.getContact());
        fillRequestWithPhones(dto.getPhones());
        fillRequestWithAttachments(dto.getAttachments());
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
        request.setAttribute("country", contact.getCountry());
        request.setAttribute("city", contact.getCity());
        request.setAttribute("address", contact.getAddress());
        String zip = MyStringUtils.intToString(contact.getZip());
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

    private ContactFrontDto buildContactFromRequest() throws ServletException, IOException {
        ContactFrontDto dto = new ContactFrontDto();
        dto.setContact(buildContactInfoFromRequest());
        dto.setPhoto(buildPhotoFromRequest());

        dto.setUpdatedPhones(buildUpdatedPhonesFromRequest());
        dto.setAddedPhones(buildAddedPhonesFromRequest());
        dto.setDeletedPhonesIds(buildDeletedPhonesIdsFromRequest());

        dto.setUpdatedAttachments(buildUpdatedAttachmentsFromRequest());
        dto.setAddedAttachments(buildAddedAttachmentsFromRequest());
        dto.setDeletedAttachmentsIds(buildDeletedAttachmentsIdsFromRequest());

        return dto;
    }

    private Contact buildContactInfoFromRequest() {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        Date dateOfBirth = MyStringUtils.emptyToDate(request.getParameter("dateOfBirth"));
        String sex = request.getParameter("sex");
        String citizenship = request.getParameter("citizenship");
        String maritalStatus = request.getParameter("maritalStatus");
        String website = request.getParameter("website");
        String email = request.getParameter("email");
        String placeOfWork = request.getParameter("placeOfWork");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        int zip = MyStringUtils.emptyToInt(request.getParameter("zip"));

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
                .placeOfWork(placeOfWork)
                .country(country)
                .city(city)
                .address(address)
                .zip(zip);

        return builder.build();
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
        return MyStringUtils.stringArrayToListOfLongs(request.getParameterValues("phoneToDelete"));
    }

    private List<AttachmentDto> buildAddedAttachmentsFromRequest() throws ServletException, IOException {
        String addedPhonesAttachments = request.getParameter("attachmentsToAdd");
        List<AttachmentDto> attachments = parseAttachmentsFromJSON(addedPhonesAttachments);
        attachments = tiePartsWithNames(attachments, "addedAttachment");

        return attachments;
    }

    private List<AttachmentDto> buildUpdatedAttachmentsFromRequest() throws ServletException, IOException {
        String updatedPhonesAttachments = request.getParameter("attachmentsToUpdate");
        List<AttachmentDto> attachments = parseAttachmentsFromJSON(updatedPhonesAttachments);
        attachments = tiePartsWithNames(attachments, "updatedAttachment");

        return attachments;
    }

    private List<Long> buildDeletedAttachmentsIdsFromRequest() {
        return MyStringUtils.stringArrayToListOfLongs(request.getParameterValues("attachmentToDelete"));
    }

    private Part buildPhotoFromRequest() throws IOException, ServletException {
        return request.getPart("photo");
    }

    private List<AttachmentDto> parseAttachmentsFromJSON(String json) {
        JSONArray addedAttachments = new JSONArray(json);

        List<AttachmentDto> attachments = new ArrayList<>();
        for (int i = 0; i < addedAttachments.length(); i++) {
            JSONObject object = addedAttachments.getJSONObject(i);
            AttachmentDto attachment = new AttachmentDto();
            attachment.setId(Long.valueOf(object.getString("id")));
            attachment.setName(object.getString("name"));
            // todo date
            attachment.setComment(object.getString("comment"));
            attachment.setFileName(object.getString("fileName"));
            attachments.add(attachment);
        }

        return attachments;
    }

    private List<AttachmentDto> tiePartsWithNames(List<AttachmentDto> attachments, String partName) throws ServletException, IOException {
        List<Part> parts = request.getParts().stream().filter(part -> partName.equals(part.getName())).collect(Collectors.toList());
        for (Part part : parts) {
            for (AttachmentDto attachment : attachments) {
                if (part.getSubmittedFileName().equals(attachment.getFileName())) {
                    attachment.setFile(part);
                }
            }
        }

        return attachments;
    }

}
