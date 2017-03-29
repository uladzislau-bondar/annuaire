package service;


import dao.AttachmentDao;
import dao.ContactDao;
import dao.PhoneDao;
import db.TransactionHandler;
import dto.ContactDatabaseDto;
import dto.ContactFrontDto;
import entities.Attachment;
import entities.Contact;
import entities.Phone;

import java.sql.Connection;
import java.util.List;

public class ContactService {
    public ContactDatabaseDto get(Long id){
        final ContactDatabaseDto dto = new ContactDatabaseDto();
        TransactionHandler.run(connection -> {
            dto.setContact(getContact(connection, id));
            dto.setPhones(getPhones(connection, id));
            dto.setAttachments(getAttachments(connection, id));
        });

        return dto;
    }

    public void save(ContactFrontDto dto){
        TransactionHandler.run(connection -> {
            Long id = saveContact(connection, dto.getContact());
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void update(ContactFrontDto dto, Long id){
        TransactionHandler.run(connection -> {
            dto.getContact().setId(id);
            updateContact(connection, dto.getContact());
            savePhones(connection, dto, id);
            saveAttachments(connection, dto, id);
        });
    }

    public void delete(Long id){
        TransactionHandler.run(connection -> {
            deleteContact(connection, id);
        });
    }

    private Long saveContact(Connection connection, Contact contact){
        ContactDao dao = new ContactDao(connection);
        return dao.save(contact);
    }

    private void updateContact(Connection connection, Contact contact){
        ContactDao dao = new ContactDao(connection);
        dao.update(contact);
    }

    private void deleteContact(Connection connection, Long id){
        ContactDao dao = new ContactDao(connection);
        dao.delete(id);
    }

    private Contact getContact(Connection connection, Long id){
        ContactDao dao = new ContactDao(connection);
        return dao.getById(id);
    }

    private List<Phone> getPhones(Connection connection, Long contactId){
        PhoneDao dao = new PhoneDao(connection);
        return dao.getByContactId(contactId);
    }

    private List<Attachment> getAttachments(Connection connection, Long contactId){
        AttachmentDao dao = new AttachmentDao(connection);
        return dao.getByContactId(contactId);
    }

    private void savePhones(Connection connection, ContactFrontDto dto, Long contactId){
        PhoneDao dao = new PhoneDao(connection);
        for (Phone updatedPhone : dto.getUpdatedPhones()) {
            updatedPhone.setContactId(contactId);
            dao.update(updatedPhone);
        }
        for (Phone addedPhone : dto.getAddedPhones()) {
            addedPhone.setContactId(contactId);
            dao.save(addedPhone);
        }
        for (Long id : dto.getDeletedPhonesIds()) {
            dao.delete(id);
        }
    }

    private void saveAttachments(Connection connection, ContactFrontDto dto, Long contactId) {
        AttachmentDao dao = new AttachmentDao(connection);
        for (Attachment updatedAttachment : dto.getUpdatedAttachments()) {
            updatedAttachment.setContactId(contactId);
            dao.update(updatedAttachment);
        }
        for (Attachment addedAttachment : dto.getAddedAttachments()) {
            addedAttachment.setContactId(contactId);
            dao.save(addedAttachment);
        }
        for (Long id : dto.getDeletedAttachmentsIds()) {
            dao.delete(id);
        }
    }
}
