package dto;


import entities.Attachment;
import entities.Contact;
import entities.Phone;

import java.util.List;

public class ContactDatabaseDto {
    private Contact contact;
    private List<Phone> phones;
    private List<Attachment> attachments;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
