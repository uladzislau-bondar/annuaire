package service;


import dao.AddressDao;
import dao.AttachmentDao;
import dao.ContactDao;
import dao.PhoneDao;
import db.ConnectionPool;
import entities.Address;
import entities.Attachment;
import entities.Contact;
import entities.Phone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UpdateContactService {
    private Contact contact;
    private Address address;
    private List<Phone> addedPhones;
    private List<Phone> updatedPhones;
    private List<Long> deletedPhonesIds;
    private List<Attachment> addedAttachments;
    private List<Attachment> updatedAttachments;
    private List<Long> deletedAttachmentsIds;
    // todo photo

    public UpdateContactService(Contact contact, Address address, List<Phone> addedPhones, List<Phone> updatedPhones, List<Long> deletedPhonesIds, List<Attachment> addedAttachments, List<Attachment> updatedAttachments, List<Long> deletedAttachmentsIds) {
        this.contact = contact;
        this.address = address;
        this.addedPhones = addedPhones;
        this.updatedPhones = updatedPhones;
        this.deletedPhonesIds = deletedPhonesIds;
        this.addedAttachments = addedAttachments;
        this.updatedAttachments = updatedAttachments;
        this.deletedAttachmentsIds = deletedAttachmentsIds;
    }

    // todo validate

    public void update() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            process(connection);

            connection.commit();
        } catch (SQLException e) {
            //todo msg
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException e1) {
                //todo msg
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                //todo msg
            }
        }
    }

    private void process(Connection connection) {
        Long contactId = updateContact(connection);
        updateAddress(connection, contactId);
        processPhones(connection, contactId);
        processAttachments(connection, contactId);
    }

    private Long updateContact(Connection connection) {
        ContactDao dao = new ContactDao(connection);
        dao.update(contact);

        return contact.getId();
    }

    private void updateAddress(Connection connection, Long contactId) {
        AddressDao dao = new AddressDao(connection);
        address.setContactId(contactId);
        dao.update(address);
    }

    private void processPhones(Connection connection, Long contactId) {
        PhoneDao dao = new PhoneDao(connection);
        for (Phone updatedPhone : updatedPhones) {
            updatedPhone.setContactId(contactId);
            dao.update(updatedPhone);
        }
        for (Phone addedPhone : addedPhones) {
            addedPhone.setContactId(contactId);
            dao.save(addedPhone);
        }
        for (Long id : deletedPhonesIds) {
            dao.delete(id);
        }
    }

    private void processAttachments(Connection connection, Long contactId) {
        AttachmentDao dao = new AttachmentDao(connection);
        for (Attachment updatedAttachment : updatedAttachments) {
            updatedAttachment.setContactId(contactId);
            dao.update(updatedAttachment);
        }
        for (Attachment addedAttachment : addedAttachments) {
            addedAttachment.setContactId(contactId);
            dao.save(addedAttachment);
        }
        for (Long id : deletedAttachmentsIds) {
            dao.delete(id);
        }
    }
}
