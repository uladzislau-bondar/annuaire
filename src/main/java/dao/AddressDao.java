package dao;


import db.constants.AddressConstants;
import db.constants.ContactConstants;
import entities.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDao extends AbstractTemplateDao<Address, Long> {
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
        List<Address> addresses = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(AddressConstants.GET_ALL)){
            ResultSet set = statement.executeQuery();
            addresses = fillListFromResultSet(set);
        } catch (SQLException e){
            logger.error(e);
        }

        return addresses;
    }

    @Override
    public Address getById(Long id) {
        Address address = null;

        try (PreparedStatement statement = getPreparedStatement(AddressConstants.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();

            address = fillAddressFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return address;
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public void delete(Long id) {

    }

    private List<Address> fillListFromResultSet(ResultSet set) throws SQLException{
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

    private Address fillAddressFromResultSet(ResultSet set) throws SQLException{
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
