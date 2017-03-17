package util;

import dto.ContactDto;
import dto.PhoneDto;
import entities.Address;
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

    private static String createFullName(Contact contact){
        return nullToEmpty(contact.getFirstName()) + " "
                + nullToEmpty(contact.getMiddleName()) + " "
                + nullToEmpty(contact.getLastName());
    }

    private static String createAddress(Address address){
        return "fakeAddress";
    }

    private static String createNumber(Phone phone) {
        return "fakeNumber";
    }

    private static String nullToEmpty(String value){
        return value == null ? "" : value;
    }
}
