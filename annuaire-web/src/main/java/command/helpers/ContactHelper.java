package command.helpers;


import com.annuaire.builders.ContactBuilder;
import com.annuaire.dto.*;
import com.annuaire.entities.Contact;
import com.annuaire.entities.Phone;
import org.json.JSONArray;
import org.json.JSONObject;
import com.annuaire.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactHelper extends AbstractHelper {
    private static final Long FAKE_ID = 1L;

    public ContactHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public ContactFrontDto getContact() throws ServletException, IOException {
        ContactFrontDto dto = new ContactFrontDto();
        dto.setContact(parseContact());
        dto.setPhoto(parsePhoto());

        dto.setUpdatedPhones(parseUpdatedPhones());
        dto.setAddedPhones(parseAddedPhones());
        dto.setDeletedPhonesIds(parseDeletedPhonesIds());

        dto.setUpdatedAttachments(parseUpdatedAttachments());
        dto.setAddedAttachments(parseAddedAttachments());
        dto.setDeletedAttachmentsIds(parseDeletedAttachmentsIds());

        return dto;
    }

    public void showContact(ContactDatabaseDto contact) throws ServletException{
        if (contactExists(contact)) {
            fillRequestWithContact(contact.getContact());
            fillRequestWithPhones(contact.getPhones());
            fillRequestWithAttachments(contact.getAttachments());
        } else {
            throw new ServletException("Contact doesn't exist");
        }
    }

    private boolean contactExists(ContactDatabaseDto contact) {
        return contact.getContact().getFirstName() != null;
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
        request.setAttribute("zip", contact.getZip());
        request.setAttribute("photoPath", contact.getPhotoPath());
    }

    private void fillRequestWithPhones(List<PhoneInfoDto> phones) {
        request.setAttribute("phones", phones);
    }

    private void fillRequestWithAttachments(List<AttachmentInfoDto> attachments) {
        request.setAttribute("attachments", attachments);
    }

    private Contact parseContact() {
        ContactBuilder builder = new ContactBuilder();
        builder.firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .middleName(request.getParameter("middleName"))
                .dateOfBirth(Utils.emptyToDate(request.getParameter("dateOfBirth")))
                .sex(request.getParameter("sex"))
                .citizenship(request.getParameter("citizenship"))
                .maritalStatus(request.getParameter("maritalStatus"))
                .website(request.getParameter("website"))
                .email(request.getParameter("email"))
                .placeOfWork(request.getParameter("placeOfWork"))
                .country(request.getParameter("country"))
                .city(request.getParameter("city"))
                .address(request.getParameter("address"))
                .zip(request.getParameter("zip"));

        return builder.build();
    }

    private Part parsePhoto() throws ServletException, IOException {
        return request.getPart("photo");
    }

    private List<Phone> parseAddedPhones() {
        String json = request.getParameter("phonesToAdd");
        List<Phone> phones = parsePhonesFromJSON(json);
        for (Phone phone : phones) {
            phone.setId(FAKE_ID);
        }

        return phones;
    }

    private List<Phone> parseUpdatedPhones() {
        String json = request.getParameter("phonesToUpdate");
        return parsePhonesFromJSON(json);
    }

    private List<Long> parseDeletedPhonesIds() {
        return Utils.toLongList(request.getParameterValues("phoneToDelete"));
    }

    private List<AttachmentFrontDto> parseAddedAttachments() throws ServletException, IOException {
        String addedPhonesAttachments = request.getParameter("attachmentsToAdd");
        List<AttachmentFrontDto> attachments = parseAttachmentsFromJSON(addedPhonesAttachments);
        for (AttachmentFrontDto attachment : attachments) {
            attachment.setId(FAKE_ID);
        }
        attachments = tiePartsWithNames(attachments, "addedAttachment");

        return attachments;
    }

    private List<AttachmentFrontDto> parseUpdatedAttachments() throws ServletException, IOException {
        String updatedPhonesAttachments = request.getParameter("attachmentsToUpdate");
        List<AttachmentFrontDto> attachments = parseAttachmentsFromJSON(updatedPhonesAttachments);
        attachments = tiePartsWithNames(attachments, "updatedAttachment");

        return attachments;
    }

    private List<Long> parseDeletedAttachmentsIds() {
        return Utils.toLongList(request.getParameterValues("attachmentToDelete"));
    }

    private List<Phone> parsePhonesFromJSON(String json) {
        JSONArray phonesInJson = new JSONArray(json);

        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < phonesInJson.length(); i++) {
            JSONObject object = phonesInJson.getJSONObject(i);
            Phone phone = new Phone();
            phone.setId(Long.valueOf(object.getString("id")));
            phone.setCountryCode(object.getString("countryCode"));
            phone.setOperatorCode(object.getString("operatorCode"));
            phone.setNumber(object.getString("number"));
            phone.setType(object.getString("type"));
            phone.setComment(object.getString("comment"));

            phones.add(phone);
        }

        return phones;
    }

    private List<AttachmentFrontDto> parseAttachmentsFromJSON(String json) {
        JSONArray addedAttachments = new JSONArray(json);

        List<AttachmentFrontDto> attachments = new ArrayList<>();
        for (int i = 0; i < addedAttachments.length(); i++) {
            JSONObject object = addedAttachments.getJSONObject(i);
            AttachmentFrontDto attachment = new AttachmentFrontDto();
            attachment.setId(Long.valueOf(object.getString("id")));
            attachment.setName(object.getString("name"));
            attachment.setDateOfUpload(Date.valueOf(object.getString("dateOfUpload")));
            attachment.setComment(object.getString("comment"));
            attachment.setFileName(object.getString("fileName"));
            attachments.add(attachment);
        }

        return attachments;
    }

    private List<AttachmentFrontDto> tiePartsWithNames(List<AttachmentFrontDto> attachments, String partName) throws ServletException, IOException {
        List<Part> parts = request.getParts().stream().filter(part -> partName.equals(part.getName())).collect(Collectors.toList());
        for (Part part : parts) {
            for (AttachmentFrontDto attachment : attachments) {
                if (part.getSubmittedFileName().equals(attachment.getFileName())) {
                    attachment.setFile(part);
                }
            }
        }

        return attachments;
    }
}
