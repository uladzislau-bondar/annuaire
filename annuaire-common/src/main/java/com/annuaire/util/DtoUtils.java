package com.annuaire.util;

import com.annuaire.dto.AttachmentInfoDto;
import org.apache.commons.lang3.StringUtils;

import com.annuaire.dto.AttachmentFrontDto;
import com.annuaire.dto.ContactInfoDto;
import com.annuaire.dto.PhoneInfoDto;
import com.annuaire.entities.Attachment;
import com.annuaire.entities.Contact;
import com.annuaire.entities.Phone;


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

    public static PhoneInfoDto convertToDto(Phone phone){
        PhoneInfoDto dto = new PhoneInfoDto();
        dto.setId(phone.getId());
        String number = createNumber(phone);
        dto.setNumber(number);
        dto.setType(phone.getType());
        dto.setComment(phone.getComment());

        return dto;
    }

    public static AttachmentInfoDto convertToDto(Attachment attachment){
        AttachmentInfoDto dto = new AttachmentInfoDto();
        dto.setId(attachment.getId());
        dto.setName(attachment.getName());
        dto.setDateOfUpload(attachment.getDateOfUpload());
        dto.setComment(attachment.getComment());
        dto.setFileName(attachment.getFileName());

        return dto;
    }

    public static Attachment convertToAttachment(AttachmentFrontDto dto){
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
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotEmpty(phone.getCountryCode())){
            result.append("+");
            result.append(phone.getCountryCode());
            result.append("-");
        }
        if (StringUtils.isNotEmpty(phone.getOperatorCode())){
            result.append(phone.getOperatorCode());
            result.append("-");
        }
        if (StringUtils.isNotEmpty(phone.getNumber())){
            result.append(phone.getNumber());
        }

        return result.toString();
    }

}
