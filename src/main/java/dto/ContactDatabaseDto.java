package dto;


import entities.Contact;

import java.util.List;

public class ContactDatabaseDto {
    private Contact contact;
    private List<PhoneInfoDto> phones;
    private List<AttachmentInfoDto> attachments;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<PhoneInfoDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneInfoDto> phones) {
        this.phones = phones;
    }

    public List<AttachmentInfoDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentInfoDto> attachments) {
        this.attachments = attachments;
    }
}
