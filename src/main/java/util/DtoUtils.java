package util;

import dto.ContactDto;
import entities.Address;
import entities.Contact;


public class DtoUtils {
    public static ContactDto convertToDto(Contact contact){
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        String fullName = createFullName(contact.getFirstName(), contact.getLastName());
        dto.setFullName(fullName);
        dto.setDateOfBirth(contact.getDateOfBirth());
        String address = createAddress(contact.getAddress());
        dto.setAddress(address);
        return dto;
    }

    private static String createFullName(String firstName, String lastName){
        return firstName + " " + lastName;
    }

    private static String createAddress(Address address){
        return nullToEmpty(address.getCountry())
                + nullToEmpty(address.getCity())
                + nullToEmpty(address.getCountry())
                + nullToEmpty(address.getAddress())
                + address.getZip();
    }

    private static String nullToEmpty(String str) {
        return str == null ? "" : (str + " ");
    }

    private static String zeroToEmpty(int i) {
        return i == 0 ? "" : String.valueOf(i);
    }
}
