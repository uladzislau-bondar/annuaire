package com.annuaire.dao;


import com.annuaire.db.constants.ContactConstants;
import com.annuaire.entities.Contact;
import com.annuaire.builders.ContactBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactDao extends AbstractTemplateDao<Contact, Long> {
    private final static Logger logger = LogManager.getLogger(ContactDao.class);

    public ContactDao(Connection connection) {
        super(connection);
    }

    @Override
    public Long save(Contact contact) throws SQLException {
        PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(ContactConstants.SAVE);
        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getLastName());
        statement.setString(3, contact.getMiddleName());
        statement.setDate(4, contact.getDateOfBirth());
        statement.setString(5, contact.getSex().value());
        statement.setString(6, contact.getCitizenship());
        statement.setString(7, contact.getMaritalStatus());
        statement.setString(8, contact.getWebsite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getPlaceOfWork());
        statement.setString(11, contact.getPhotoPath());
        statement.setString(12, contact.getCountry());
        statement.setString(13, contact.getCity());
        statement.setString(14, contact.getAddress());
        statement.setString(15, contact.getZip());

        logger.info(statement.toString());

        statement.executeUpdate();

        return obtainIdFromStatement(statement);
    }

    @Override
    public List<Contact> getAll() throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_ALL);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();


        return parseContactListFromResultSet(set);
    }

    @Override
    public Contact getById(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseContactFromResultSet(set);
    }

    @Override
    public void update(Contact contact) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.UPDATE);
        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getLastName());
        statement.setString(3, contact.getMiddleName());
        statement.setDate(4, contact.getDateOfBirth());
        statement.setString(5, contact.getSex().value());
        statement.setString(6, contact.getCitizenship());
        statement.setString(7, contact.getMaritalStatus());
        statement.setString(8, contact.getWebsite());
        statement.setString(9, contact.getEmail());
        statement.setString(10, contact.getPlaceOfWork());
        statement.setString(11, contact.getPhotoPath());
        statement.setString(12, contact.getCountry());
        statement.setString(13, contact.getCity());
        statement.setString(14, contact.getAddress());
        statement.setString(15, contact.getZip());
        statement.setLong(16, contact.getId());

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.DELETE);
        statement.setLong(1, id);

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    public List<Contact> getByWithOffset(Map<String, String> params, int limit, int offset) throws SQLException {
        String query = buildSearchQuery(params, limit, offset);

        logger.info(query);

        PreparedStatement statement = getPreparedStatement(query);
        ResultSet set = statement.executeQuery();

        return parseContactListFromResultSet(set);
    }

    public List<Contact> getWithOffset(int limit, int offset) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_WITH_OFFSET);
        statement.setInt(1, limit);
        statement.setInt(2, offset);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseContactListFromResultSet(set);
    }

    public String getEmailById(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_EMAIL_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseEmailFromResultSet(set);
    }

    public String getPhotoPathById(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_PHOTOPATH_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parsePhotoPathFromResultSet(set);
    }

    public List<String> getEmailsByDateOfBirth(Date date) throws SQLException {
        PreparedStatement statement = getPreparedStatement(ContactConstants.GET_EMAILS_BY_DATE_OF_BIRTH);
        statement.setDate(1, date);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseEmailListFromResultSet(set);
    }

    public void updatePhotoPathById(String photoPath, Long id) throws SQLException{
        PreparedStatement statement = getPreparedStatement(ContactConstants.UPDATE_PHOTOPATH);
        statement.setString(1, photoPath);
        statement.setLong(2, id);

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    private List<Contact> parseContactListFromResultSet(ResultSet set) throws SQLException {
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
                    .website(set.getString("website"))
                    .email(set.getString("email"))
                    .placeOfWork(set.getString("placeOfWork"))
                    .country(set.getString("country"))
                    .city(set.getString("city"))
                    .address(set.getString("address"))
                    .zip(set.getString("zip"))
                    .photoPath(set.getString("photoPath"));

            Contact contact = builder.build();
            contacts.add(contact);
        }

        return contacts;
    }

    private Contact parseContactFromResultSet(ResultSet set) throws SQLException {
        Contact contact = null;

        if (set.next()) {
            ContactBuilder builder = new ContactBuilder();
            builder.id(set.getLong("id"))
                    .firstName(set.getString("firstName"))
                    .lastName(set.getString("lastName"))
                    .middleName(set.getString("middleName"))
                    .dateOfBirth(set.getDate("dateOfBirth"))
                    .sex(set.getString("sex"))
                    .citizenship(set.getString("citizenship"))
                    .maritalStatus(set.getString("maritalStatus"))
                    .website(set.getString("website"))
                    .email(set.getString("email"))
                    .placeOfWork(set.getString("placeOfWork"))
                    .country(set.getString("country"))
                    .city(set.getString("city"))
                    .address(set.getString("address"))
                    .zip(set.getString("zip"))
                    .photoPath(set.getString("photoPath"));

            contact = builder.build();
        }

        return contact;
    }

    private String parseEmailFromResultSet(ResultSet set) throws SQLException {
        String email = null;
        if (set.next()) {
            email = set.getString("email");
        }

        return email;
    }

    private List<String> parseEmailListFromResultSet(ResultSet set) throws SQLException {
        List<String> emails = new ArrayList<>();
        while (set.next()) {
            String email = set.getString("email");
            emails.add(email);
        }

        return emails;
    }

    private String parsePhotoPathFromResultSet(ResultSet set) throws SQLException {
        String path = null;
        if (set.next()) {
            path = set.getString("photoPath");
        }

        return path;
    }

    private String buildSearchQuery(Map<String, String> params, int limit, int offset) {
        StringBuilder query = new StringBuilder();
        query.append(ContactConstants.GET_BY);

        if (StringUtils.isNotEmpty(params.get("firstName"))) {
            query.append(appendParamValue("firstName", params.get("firstName")));
        }
        if (StringUtils.isNotEmpty(params.get("lastName"))) {
            query.append(appendParamValue("lastName", params.get("lastName")));
        }
        if (StringUtils.isNotEmpty(params.get("middleName"))) {
            query.append(appendParamValue("middleName", params.get("middleName")));
        }
        if (StringUtils.isNotEmpty(params.get("dateOfBirth")) && StringUtils.isNotEmpty(params.get("dateRange"))) {
            query.append(appendDateOfBirth(params.get("dateOfBirth"), params.get("dateRange")));
        }
        if (StringUtils.isNotEmpty(params.get("sex"))) {
            query.append(appendParamValue("sex", params.get("sex")));
        }
        if (StringUtils.isNotEmpty(params.get("citizenship"))) {
            query.append(appendParamValue("citizenship", params.get("citizenship")));
        }
        if (StringUtils.isNotEmpty(params.get("maritalStatus"))) {
            query.append(appendParamValue("maritalStatus", params.get("maritalStatus")));
        }
        if (StringUtils.isNotEmpty(params.get("country"))) {
            query.append(appendParamValue("country", params.get("country")));
        }
        if (StringUtils.isNotEmpty(params.get("city"))) {
            query.append(appendParamValue("city", params.get("city")));
        }
        if (StringUtils.isNotEmpty(params.get("address"))) {
            query.append(appendParamValue("address", params.get("address")));
        }
        if (StringUtils.isNotEmpty(params.get("zip"))) {
            query.append(appendParamValue("zip", params.get("zip")));
        }

        query = removeLastAndFromQuery(query.toString());
        query.append(appendQueryEnd(limit, offset));

        logger.info(query.toString());

        return query.toString();
    }

    private String appendParamValue(String param, String value) {
        StringBuilder builder = new StringBuilder();
        builder.append(param);
        builder.append(" ");
        builder.append("LIKE '%");
        builder.append(value);
        builder.append("%'");
        builder.append(" AND ");

        return builder.toString();
    }

    private String appendDateOfBirth(String date, String range) {
        StringBuilder builder = new StringBuilder();
        builder.append("dateOfBirth");
        if (range.equals("before")) {
            builder.append(" < ");
        } else if (range.equals("after")) {
            builder.append(" > ");
        }
        builder.append("'");
        builder.append(date);
        builder.append("'");
        builder.append(" AND ");

        return builder.toString();
    }

    private String appendQueryEnd(int limit, int offset) {
        StringBuilder builder = new StringBuilder();
        builder.append("LIMIT ");
        builder.append(limit);
        builder.append(" OFFSET ");
        builder.append(offset);

        return builder.toString();
    }

    private StringBuilder removeLastAndFromQuery(String query) {
        if (query.endsWith("AND ")) {
            query = query.substring(0, query.length() - 4);
        }

        return new StringBuilder(query);
    }
}
