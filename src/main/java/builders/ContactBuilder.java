package builders;

import entities.Contact;
import enums.Sex;

import java.io.File;
import java.sql.Date;

public class ContactBuilder {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private Sex sex;
    private String citizenship;
    private String maritalStatus;
    private String website;
    private String email;
    private String placeOfWork;
    private File photo;
    private String country;
    private String city;
    private String address;
    private int zip;

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
        if (sex == null || sex.isEmpty()) {
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

    public ContactBuilder website(String website) {
        this.website = website;
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

    public ContactBuilder photo(File photo) {
        this.photo = photo;
        return this;
    }

    public ContactBuilder country(String country) {
        this.country = country;
        return this;
    }

    public ContactBuilder city(String city) {
        this.city = city;
        return this;
    }

    public ContactBuilder address(String address) {
        this.address = address;
        return this;
    }

    public ContactBuilder zip(int zip) {
        this.zip = zip;
        return this;
    }

    public Contact build() {
        return new Contact(id, firstName, lastName, middleName, dateOfBirth, sex, citizenship, maritalStatus, website, email, placeOfWork, photo, country, city, address, zip);
    }
}