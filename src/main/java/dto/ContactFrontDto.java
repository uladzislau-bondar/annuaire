package dto;


import entities.Attachment;
import entities.Contact;
import entities.Phone;

import javax.servlet.http.Part;
import java.util.List;

public class ContactFrontDto {
    private Contact contact;
    private Part photo;
    private List<Phone> addedPhones;
    private List<Phone> updatedPhones;
    private List<Long> deletedPhonesIds;
    private List<AttachmentDto> addedAttachments;
    private List<AttachmentDto> updatedAttachments;
    private List<Long> deletedAttachmentsIds;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Phone> getAddedPhones() {
        return addedPhones;
    }

    public void setAddedPhones(List<Phone> addedPhones) {
        this.addedPhones = addedPhones;
    }

    public List<Phone> getUpdatedPhones() {
        return updatedPhones;
    }

    public void setUpdatedPhones(List<Phone> updatedPhones) {
        this.updatedPhones = updatedPhones;
    }

    public List<Long> getDeletedPhonesIds() {
        return deletedPhonesIds;
    }

    public void setDeletedPhonesIds(List<Long> deletedPhonesIds) {
        this.deletedPhonesIds = deletedPhonesIds;
    }

    public List<AttachmentDto> getAddedAttachments() {
        return addedAttachments;
    }

    public void setAddedAttachments(List<AttachmentDto> addedAttachments) {
        this.addedAttachments = addedAttachments;
    }

    public List<AttachmentDto> getUpdatedAttachments() {
        return updatedAttachments;
    }

    public void setUpdatedAttachments(List<AttachmentDto> updatedAttachments) {
        this.updatedAttachments = updatedAttachments;
    }

    public List<Long> getDeletedAttachmentsIds() {
        return deletedAttachmentsIds;
    }

    public void setDeletedAttachmentsIds(List<Long> deletedAttachmentsIds) {
        this.deletedAttachmentsIds = deletedAttachmentsIds;
    }

    public Part getPhoto() {
        return photo;
    }

    public void setPhoto(Part photo) {
        this.photo = photo;
    }
}
