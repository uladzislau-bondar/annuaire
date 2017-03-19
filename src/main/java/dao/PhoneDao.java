package dao;


import db.constants.PhoneConstants;
import entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Phone getById(Long id) {
        return null;
    }

    @Override
    public void update(Phone entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
