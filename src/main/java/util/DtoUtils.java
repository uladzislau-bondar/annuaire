package util;

import dto.ContactDto;
import entities.Address;
import entities.Contact;


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

    private static String createFullName(Contact contact){
        return nullToEmpty(contact.getFirstName()) + " "
                + nullToEmpty(contact.getMiddleName()) + " "
                + nullToEmpty(contact.getLastName());
    }

    private static String createAddress(Address address){
        return "address";
    }

    private static String nullToEmpty(String value){
        return value == null ? "" : value;
    }
}
