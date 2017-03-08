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

    //todo deal with null
    private static String createAddress(Address address){
//        return address.getCountry() + ", " + address.getCity() + ", "
//                + address.getCountry() + ", " + address.getAddress() + ", "
//                + address.getZip();
        return address.getAddress();

    }
}
