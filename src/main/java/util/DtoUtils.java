package util;

import dto.AttachmentDto;
import dto.ContactDto;
import dto.PhoneDto;
import entities.Address;
import entities.Attachment;
import entities.Contact;
import entities.Phone;


public class DtoUtils {
    public static ContactDto convertToDto(Contact contact){
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        String fullName = createFullName(contact);
        dto.setFullName(fullName);
        dto.setDateOfBirth(contact.getDateOfBirth());
        String address = createAddress(contact.getAddress());
        dto.setAddress(address);

        return dto;
    }

    public static PhoneDto convertToDto(Phone phone){
        PhoneDto dto = new PhoneDto();
        dto.setId(phone.getId());
        String number = createNumber(phone);
        dto.setNumber(number);
        dto.setType(phone.getType());
        dto.setComment(phone.getComment());

        return dto;
    }

    public static AttachmentDto convertToDto(Attachment attachment){
        AttachmentDto dto = new AttachmentDto();
        dto.setId(attachment.getId());
        dto.setName(attachment.getName());
        dto.setDateOfUpload(attachment.getDateOfUpload());
        dto.setComment(attachment.getComment());

        return dto;
    }

    private static String createFullName(Contact contact){
        return StringUtils.nullToEmpty(contact.getFirstName()) + " "
                + StringUtils.nullToEmpty(contact.getMiddleName()) + " "
                + StringUtils.nullToEmpty(contact.getLastName());
    }

    private static String createAddress(Address address){
        return StringUtils.nullToEmpty(address.getCountry()) + "/"
                + StringUtils.nullToEmpty(address.getCity()) + "/"
                + StringUtils.nullToEmpty(address.getAddress());
    }

    private static String createNumber(Phone phone) {
        return "+" + StringUtils.intToString(phone.getCountryCode()) + "-"
                + StringUtils.intToString(phone.getNumber());
    }

}
