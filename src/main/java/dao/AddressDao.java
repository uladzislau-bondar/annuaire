package dao;


import db.constants.AddressConstants;
import entities.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDao extends AbstractTemplateDao<Address, Long> {
    private final static Logger logger = LogManager.getLogger(AddressDao.class);

    public AddressDao(Connection connection) {
        super(connection);
    }

    @Override
    public Long save(Address address) {
        Long id = null;

        try (PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(AddressConstants.SAVE)) {
            statement.setLong(1, address.getContactId());
            statement.setString(2, address.getCountry());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getAddress());
            statement.setInt(5, address.getZip());

            logger.info(statement.toString());

            statement.executeUpdate();
            id = obtainIdFromStatement(statement);
        } catch (SQLException e) {
            logger.error(e);
        }

        return id;
    }

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(AddressConstants.GET_ALL)) {
            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();
            addresses = fillListFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return addresses;
    }

    @Override
    public Address getById(Long id) {
        Address address = null;

        try (PreparedStatement statement = getPreparedStatement(AddressConstants.GET_BY_ID)) {
            statement.setLong(1, id);

            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();

            address = fillAddressFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return address;
    }

    @Override
    public void update(Address address) {
        try (PreparedStatement statement = getPreparedStatement(AddressConstants.UPDATE_BY_CONTACT_ID)) {
            statement.setLong(1, address.getContactId());
            statement.setString(2, address.getCountry());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getAddress());
            statement.setInt(5, address.getZip());
            statement.setLong(6, address.getContactId());

            logger.info(statement.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = getPreparedStatement(AddressConstants.DELETE)) {
            statement.setLong(1, id);

            logger.info(statement.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public Address getByContactId(Long contactId){
        Address address = null;

        try (PreparedStatement statement = getPreparedStatement(AddressConstants.GET_BY_CONTACT_ID)) {
            statement.setLong(1, contactId);

            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();

            address = fillAddressFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return address;
    }

    private List<Address> fillListFromResultSet(ResultSet set) throws SQLException {
        List<Address> addresses = new ArrayList<>();

        while (set.next()) {
            Address address = new Address();
            address.setId(set.getLong("id"));
            address.setContactId(set.getLong("contactId"));
            address.setCountry(set.getString("country"));
            address.setCity(set.getString("city"));
            address.setAddress(set.getString("address"));
            address.setZip(set.getInt("zip"));

            addresses.add(address);
        }

        return addresses;
    }

    private Address fillAddressFromResultSet(ResultSet set) throws SQLException {
        Address address = new Address();

        if (set.next()) {
            address.setId(set.getLong("id"));
            address.setContactId(set.getLong("contactId"));
            address.setCountry(set.getString("country"));
            address.setCity(set.getString("city"));
            address.setAddress(set.getString("address"));
            address.setZip(set.getInt("zip"));
        }

        return address;
    }
}
