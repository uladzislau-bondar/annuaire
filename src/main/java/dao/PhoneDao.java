package dao;


import db.constants.PhoneConstants;
import entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao extends AbstractTemplateDao<Phone, Long> {
    private static final Logger logger = LogManager.getLogger(PhoneDao.class);

    @Override
    public Long save(Phone phone) {
        Long id = null;

        try (PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(PhoneConstants.SAVE)) {
            statement.setLong(1, phone.getContactId());
            statement.setInt(2, phone.getCountryCode());
            statement.setInt(3, phone.getNumber());
            statement.setString(4, phone.getType().value());
            statement.setString(5, phone.getComment());

            logger.info(statement.toString());

            statement.executeUpdate();
            id = obtainIdFromStatement(statement);
        } catch (SQLException e) {
            logger.error(e);
        }

        return id;
    }

    @Override
    public List<Phone> getAll() {
        List<Phone> phones = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(PhoneConstants.GET_ALL)) {
            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();
            phones = fillListFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return phones;
    }

    @Override
    public Phone getById(Long id) {
        Phone phone = null;

        try (PreparedStatement statement = getPreparedStatement(PhoneConstants.GET_BY_ID)) {
            statement.setLong(1, id);

            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();

            phone = fillPhoneFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return phone;
    }

    @Override
    public void update(Phone phone) {

    }

    @Override
    public void delete(Long id) {

    }

    private List<Phone> fillListFromResultSet(ResultSet set) throws SQLException {
        List<Phone> phones = new ArrayList<>();

        while (set.next()) {
            Phone phone = new Phone();
            phone.setId(set.getLong("id"));
            phone.setContactId(set.getLong("contactId"));
            phone.setCountryCode(set.getInt("countryCode"));
            phone.setNumber(set.getInt("number"));
            phone.setType(set.getString("type"));
            phone.setComment(set.getString("comment"));

            phones.add(phone);
        }

        return phones;
    }

    private Phone fillPhoneFromResultSet(ResultSet set) throws SQLException {
        Phone phone = new Phone();

        if (set.next()) {
            phone.setId(set.getLong("id"));
            phone.setContactId(set.getLong("contactId"));
            phone.setCountryCode(set.getInt("countryCode"));
            phone.setNumber(set.getInt("number"));
            phone.setType(set.getString("type"));
            phone.setComment(set.getString("comment"));
        }

        return phone;
    }
}
