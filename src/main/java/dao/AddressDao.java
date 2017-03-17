package dao;


import db.constants.AddressConstants;
import db.constants.ContactConstants;
import entities.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AddressDao extends AbstractTemplateDao<Address, Long {
    private final static Logger logger = LogManager.getLogger(AddressDao.class);

    @Override
    public void save(Address address) {
        try (PreparedStatement statement = getPreparedStatement(AddressConstants.SAVE)) {
            statement.setLong(1, address.getContactId());
            statement.setString(2, address.getCountry());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getAddress());
            statement.setInt(5, address.getZip());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public List<Address> getAll() {
        return null;
    }

    @Override
    public Address getById(Long id) {
        return null;
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public void delete(Long id) {

    }
}
