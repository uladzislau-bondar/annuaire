package builders;

import entities.Address;
import entities.Attachment;
import entities.Contact;
import entities.Phone;
import service.UpdateContactService;

import java.util.List;

public class UpdateContactServiceBuilder {
    private Contact contact;
    private Address address;
    private List<Phone> addedPhones;
    private List<Phone> updatedPhones;
    private List<Long> deletedPhonesIds;
    private List<Attachment> addedAttachments;
    private List<Attachment> updatedAttachments;
    private List<Long> deletedAttachmentsIds;

    public UpdateContactServiceBuilder contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public UpdateContactServiceBuilder address(Address address) {
        this.address = address;
        return this;
    }

    public UpdateContactServiceBuilder addedPhones(List<Phone> addedPhones) {
        this.addedPhones = addedPhones;
        return this;
    }

    public UpdateContactServiceBuilder updatedPhones(List<Phone> updatedPhones) {
        this.updatedPhones = updatedPhones;
        return this;
    }

    public UpdateContactServiceBuilder deletedPhonesIds(List<Long> deletedPhonesIds) {
        this.deletedPhonesIds = deletedPhonesIds;
        return this;
    }

    public UpdateContactServiceBuilder addedAttachments(List<Attachment> addedAttachments) {
        this.addedAttachments = addedAttachments;
        return this;
    }

    public UpdateContactServiceBuilder updatedAttachments(List<Attachment> updatedAttachments) {
        this.updatedAttachments = updatedAttachments;
        return this;
    }

    public UpdateContactServiceBuilder deletedAttachmentsIds(List<Long> deletedAttachmentsIds) {
        this.deletedAttachmentsIds = deletedAttachmentsIds;
        return this;
    }

    public UpdateContactService build() {
        return new UpdateContactService(contact, address, addedPhones, updatedPhones, deletedPhonesIds, addedAttachments, updatedAttachments, deletedAttachmentsIds);
    }
}