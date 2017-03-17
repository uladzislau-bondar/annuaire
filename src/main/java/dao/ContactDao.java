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
        try (PreparedStatement statement = getPreparedStatement(ContactConstants.SAVE)) {
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
        }
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(ContactConstants.GET_ALL)) {
            ResultSet set = statement.executeQuery();
            contacts = fillList(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return contacts;
    }

    @Override
    public Contact getById(Long id) {
        //todo finish filling contact
        Contact contact = null;

        try (PreparedStatement statement = getPreparedStatement(ContactConstants.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();

            if (set != null && set.next()) {
                ContactBuilder builder = new ContactBuilder();
                builder.id(set.getLong("id"))
                        .firstName(set.getString("firstName"))
                        .lastName(set.getString("lastName"))
                        .dateOfBirth(set.getDate("dateOfBirth"))
                        .placeOfWork(set.getString("placeOfWork"));

                contact = builder.build();
            }
        } catch (SQLException e) {

        }

        return contact;
    }

    @Override
    public void update(Contact contact) {
        try (PreparedStatement statement = getPreparedStatement(ContactConstants.UPDATE)){
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
            logger.info(statement.toString());
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = getPreparedStatement(ContactConstants.DELETE)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public List<Contact> getBy(String params) {
        //todo getBy specific params
        return null;
    }

    public List<Contact> getWithOffset(int limit, int offset) {
        List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(ContactConstants.GET_WITH_OFFSET)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();
            contacts = fillList(set);

        } catch (SQLException e) {
            logger.error(e);
        }

        return contacts;
    }

    private List<Contact> fillList(ResultSet set) throws SQLException {
        List<Contact> contacts = new ArrayList<>();

        while (set.next()) {
            ContactBuilder builder = new ContactBuilder();
            builder.id(set.getLong("id"))
                    .firstName(set.getString("firstName"))
                    .lastName(set.getString("lastName"))
                    .middleName(set.getString("middleName"))
                    .dateOfBirth(set.getDate("dateOfBirth"))
                    .sex(set.getString("sex"))
                    .citizenship(set.getString("citizenship"))
                    .maritalStatus(set.getString("maritalStatus"))
                    .webSite(set.getString("webSite"))
                    .email(set.getString("email"))
                    .placeOfWork(set.getString("placeOfWork"));

            Contact contact = builder.build();
            contacts.add(contact);
        }

        return contacts;
    }

    private Contact fillContact(ResultSet set) {
        return null;
    }
}
