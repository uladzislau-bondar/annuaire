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
        return contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName();
    }

    private static String createAddress(Address address){
        return "address";
    }
}
