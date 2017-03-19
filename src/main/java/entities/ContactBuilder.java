package entities;

import enums.Sex;

import java.sql.Date;
import java.util.List;

public class ContactBuilder {
    private Long id;
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private Date dateOfBirth;
    private Sex sex = Sex.UNKNOWN;
    private String citizenship = "";
    private String maritalStatus = "";
    private String webSite = "";
    private String email = "";
    private String placeOfWork = "";
    private Address address;
    private List<Phone> phones;
    private List<Attachment> attachments;
    private String photo = "";

    public ContactBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ContactBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactBuilder middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public ContactBuilder dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public ContactBuilder sex(String sex) {
        if (sex == null) {
            this.sex = Sex.UNKNOWN;
        } else {
            this.sex = Sex.valueOf(sex);
        }

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

    public ContactBuilder attachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public ContactBuilder photo(String photo) {
        this.photo = photo;
        return this;
    }

    public Contact build() {
        return new Contact(id, firstName, lastName, middleName, dateOfBirth, sex, citizenship, maritalStatus, webSite, email, placeOfWork, address, phones, attachments, photo);
    }
}