package entities;

import enums.Sex;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Contact {
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

    public Contact(Long contactId, String firstName, String lastName, String middleName, Date dateOfBirth, Sex sex, String citizenship, String maritalStatus, String webSite, String email, String placeOfWork, Address address, List<Phone> phones, File photo) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.citizenship = citizenship;
        this.maritalStatus = maritalStatus;
        this.webSite = webSite;
        this.email = email;
        this.placeOfWork = placeOfWork;
        this.address = address;
        this.phones = phones;
        this.photo = photo;
    }

    public Long getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getEmail() {
        return email;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public File getPhoto() {
        return photo;
    }
}
