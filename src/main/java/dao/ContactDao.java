package dao;


import db.constants.ContactConstants;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends AbstractTemplateDao<Contact, Long> {
    private final static Logger logger = LogManager.getLogger(ContactDao.class);


    public ContactDao() {
        super();
    }

    @Override
    public void save(Contact contact) {
        PreparedStatement statement = getPreparedStatement(ContactConstants.SAVE);

        try {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getMiddleName());
            statement.setDate(4, contact.getDateOfBirth());
            statement.setString(5, contact.getSex().value());
            statement.setString(6, contact.getCitizenship());
            statement.setString(7, contact.getMaritalStatus());
            statement.setString(8, contact.getWebSite());
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getPlaceOfWork());
            statement.setString(11, contact.getPhoto());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePreparedStatement(statement);
        }
    }

    @Override
    public List<Contact> getAll() {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_ALL);
        ResultSet rs = getResultSet(statement);

        List<Contact> contacts = new ArrayList<>();

        try {
            while (rs.next()) {
                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .middleName(rs.getString("middleName"))
                        .dateOfBirth(rs.getDate("dateOfBirth"))
                        .sex(rs.getString("sex"))
                        .citizenship(rs.getString("citizenship"))
                        .maritalStatus(rs.getString("maritalStatus"))
                        .webSite(rs.getString("webSite"))
                        .email(rs.getString("email"))
                        .placeOfWork(rs.getString("placeOfWork"));

                Contact contact = builder.build();
                contacts.add(contact);
            }
        } catch (SQLException e) {

        } finally {
            closePreparedStatement(statement);
            closeResultSet(rs);
        }

        return contacts;
    }

    @Override
    public Contact getById(Long id) {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_BY_ID);
        ResultSet rs = null;
        Contact contact = null;

        try {
            statement.setLong(1, id);
            rs = getResultSet(statement);

            if (rs != null && rs.next()) {
                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .dateOfBirth(rs.getDate("dateOfBirth"))
                        .placeOfWork(rs.getString("placeOfWork"));

                contact = builder.build();
            }
        } catch (SQLException e) {

        } finally {
            closePreparedStatement(statement);
            closeResultSet(rs);
        }

        return contact;
    }

    @Override
    public void update(Contact contact) {
        PreparedStatement statement = getPreparedStatement(ContactConstants.UPDATE);

        try {
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getMiddleName());
            statement.setDate(4, contact.getDateOfBirth());
            statement.setString(5, contact.getSex().value());
            statement.setString(6, contact.getCitizenship());
            statement.setString(7, contact.getMaritalStatus());
            statement.setString(8, contact.getWebSite());
            statement.setString(9, contact.getEmail());
            statement.setString(10, contact.getPlaceOfWork());
            statement.setString(11, contact.getPhoto());
            statement.setLong(12, contact.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePreparedStatement(statement);
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement = getPreparedStatement(ContactConstants.DELETE);

        try {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closePreparedStatement(statement);
        }
    }

    public List<Contact> getBy(String params){
        //todo getBy specific params
        return null;
    }

    public List<Contact> getWithOffset(int offset, int limit){
        return null;
    }
}
