package entities;

import enums.Sex;

import java.io.File;
import java.sql.Date;
import java.util.List;

public class Contact {
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
    private String photo = "";

    public Contact(Long id, String firstName, String lastName, String middleName, Date dateOfBirth, Sex sex, String citizenship, String maritalStatus, String webSite, String email, String placeOfWork, Address address, List<Phone> phones, String photo) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }
}
