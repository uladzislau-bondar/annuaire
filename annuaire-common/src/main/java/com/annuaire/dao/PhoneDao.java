package com.annuaire.dao;


import com.annuaire.db.constants.PhoneConstants;
import com.annuaire.entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao extends AbstractTemplateDao<Phone, Long> {
    private static final Logger logger = LogManager.getLogger(PhoneDao.class);

    public PhoneDao(Connection connection) {
        super(connection);
    }

    @Override
    public Long save(Phone phone) throws SQLException {
        PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(PhoneConstants.SAVE);
        statement.setLong(1, phone.getContactId());
        statement.setString(2, phone.getCountryCode());
        statement.setString(3, phone.getOperatorCode());
        statement.setString(4, phone.getNumber());
        statement.setString(5, phone.getType().value());
        statement.setString(6, phone.getComment());

        logger.info(statement.toString());

        statement.executeUpdate();

        return obtainIdFromStatement(statement);
    }

    @Override
    public List<Phone> getAll() throws SQLException {
        PreparedStatement statement = getPreparedStatement(PhoneConstants.GET_ALL);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseListFromResultSet(set);
    }

    @Override
    public Phone getById(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(PhoneConstants.GET_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parsePhoneFromResultSet(set);
    }

    @Override
    public void update(Phone phone) throws SQLException {
        PreparedStatement statement = getPreparedStatement(PhoneConstants.UPDATE);
        statement.setLong(1, phone.getContactId());
        statement.setString(2, phone.getCountryCode());
        statement.setString(3, phone.getOperatorCode());
        statement.setString(4, phone.getNumber());
        statement.setString(5, phone.getType().value());
        statement.setString(6, phone.getComment());
        statement.setLong(7, phone.getId());

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(PhoneConstants.DELETE);
        statement.setLong(1, id);

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    public List<Phone> getByContactId(Long contactId) throws SQLException {
        PreparedStatement statement = getPreparedStatement(PhoneConstants.GET_BY_CONTACT_ID);
        statement.setLong(1, contactId);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseListFromResultSet(set);
    }

    private List<Phone> parseListFromResultSet(ResultSet set) throws SQLException {
        List<Phone> phones = new ArrayList<>();

        while (set.next()) {
            Phone phone = new Phone();
            phone.setId(set.getLong("id"));
            phone.setContactId(set.getLong("contactId"));
            phone.setCountryCode(set.getString("countryCode"));
            phone.setOperatorCode(set.getString("operatorCode"));
            phone.setNumber(set.getString("number"));
            phone.setType(set.getString("type"));
            phone.setComment(set.getString("comment"));

            phones.add(phone);
        }

        return phones;
    }

    private Phone parsePhoneFromResultSet(ResultSet set) throws SQLException {
        Phone phone = new Phone();

        if (set.next()) {
            phone.setId(set.getLong("id"));
            phone.setContactId(set.getLong("contactId"));
            phone.setCountryCode(set.getString("countryCode"));
            phone.setOperatorCode(set.getString("operatorCode"));
            phone.setNumber(set.getString("number"));
            phone.setType(set.getString("type"));
            phone.setComment(set.getString("comment"));
        }

        return phone;
    }
}
