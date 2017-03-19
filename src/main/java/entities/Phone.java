package entities;


import enums.PhoneType;

public class Phone {
    private Long id;
    private Long contactId;
    private int countryCode;
    private int number;
    private PhoneType type;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || type.isEmpty()) {
            this.type = PhoneType.UNKNOWN;
        } else {
            this.type = PhoneType.valueOf(type);
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
