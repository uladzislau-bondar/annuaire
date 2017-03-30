package util;

import org.apache.commons.lang3.StringUtils;

import dto.AttachmentDto;
import dto.ContactInfoDto;
import dto.PhoneDto;
import entities.Attachment;
import entities.Contact;
import entities.Phone;


public class DtoUtils {
    public static ContactInfoDto convertToInfoDto(Contact contact){
        ContactInfoDto dto = new ContactInfoDto();
        dto.setId(contact.getId());
        String fullName = createFullName(contact);
        dto.setFullName(fullName);
        dto.setDateOfBirth(contact.getDateOfBirth());
        String address = createAddress(contact);
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

    public static Attachment convertToAttachment(AttachmentDto dto){
        Attachment attachment = new Attachment();
        attachment.setId(dto.getId());
        attachment.setName(dto.getName());
        attachment.setDateOfUpload(dto.getDateOfUpload());
        attachment.setComment(dto.getComment());
        attachment.setFileName(dto.getFileName());

        return attachment;
    }

    private static String createFullName(Contact contact){
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotEmpty(contact.getFirstName())){
            result.append(contact.getFirstName());
            result.append(" ");
        }
        if (StringUtils.isNotEmpty(contact.getMiddleName())){
            result.append(contact.getMiddleName());
            result.append(" ");
        }
        if (StringUtils.isNotEmpty(contact.getFirstName())){
            result.append(contact.getLastName());
        }

        return result.toString();
    }

    private static String createAddress(Contact contact){
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotEmpty(contact.getCountry())){
            result.append(contact.getCountry());
            result.append("/");
        }
        if (StringUtils.isNotEmpty(contact.getCity())){
            result.append(contact.getCity());
            result.append("/");
        }
        if (StringUtils.isNotEmpty(contact.getAddress())){
            result.append(contact.getAddress());
        }

        return result.toString();
    }

    private static String createNumber(Phone phone) {
        return "+" + MyStringUtils.intToString(phone.getCountryCode()) + "-"
                + MyStringUtils.intToString(phone.getNumber());
    }

}
