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

public class ShowContactService {
    public Contact getContactById(Long id){
        Contact contact = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            contact = buildContact(connection, id);

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

        return contact;
    }

    private Contact buildContact (Connection connection, Long id) {
        Contact contact = getContact(connection, id);
        contact.setAddress(getAddressByContactId(connection, id));
        contact.setPhones(getPhonesByContactId(connection, id));
        contact.setAttachments(getAttachmentsByContactId(connection, id));

        return contact;
    }

    private Contact getContact(Connection connection, Long id){
        ContactDao dao = new ContactDao(connection);
        return dao.getById(id);
    }

    private Address getAddressByContactId(Connection connection, Long contactId){
        AddressDao dao = new AddressDao(connection);
        return dao.getByContactId(contactId);
    }

    private List<Phone> getPhonesByContactId(Connection connection, Long contactId){
        PhoneDao dao = new PhoneDao(connection);
        return dao.getByContactId(contactId);
    }

    private List<Attachment> getAttachmentsByContactId(Connection connection, Long contactId){
        AttachmentDao dao = new AttachmentDao(connection);
        return dao.getByContactId(contactId);
    }
}
