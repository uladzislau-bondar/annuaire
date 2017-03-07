package entities;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ContactBuilder {
    private Long contactId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private Sex sex;
    private String citizenship;
    private String maritalStatus;
    private String webSite;
    private String email;
    private String placeOfWork;
    private Address address;
    private List<Phone> phones;
    private File photo;

    public ContactBuilder(Long contactId, String firstName, String lastName){
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ContactBuilder middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ContactBuilder dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public ContactBuilder sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ContactBuilder citizenship(String citizenship) {
        this.citizenship = citizenship;
        return this;
    }

    public ContactBuilder maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public ContactBuilder webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public ContactBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ContactBuilder placeOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
        return this;
    }

    public ContactBuilder address(Address address) {
        this.address = address;
        return this;
    }

    public ContactBuilder phones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }

    public ContactBuilder photo(File photo) {
        this.photo = photo;
        return this;
    }

    public Contact build() {
        return new Contact(contactId, firstName, lastName, middleName, dateOfBirth, sex, citizenship, maritalStatus, webSite, email, placeOfWork, address, phones, photo);
    }
}