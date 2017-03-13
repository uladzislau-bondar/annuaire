package dao;


import entities.Address;
import entities.Contact;
import entities.ContactBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends AbstractEntityDao<Contact, Long> {
    private final String SQL_GET_ALL = "select * from contacts c left outer join addresses a on c.addressId=a.id limit 1";

    public ContactDao(){
        super();
    }

    @Override
    public void create(Contact contact) {
    }

    @Override
    public List<Contact> getAll() {
        PreparedStatement statement = getPreparedStatement(SQL_GET_ALL);
        ResultSet rs = getResultSet(statement);

        List<Contact> contacts = new ArrayList<>();

        try{
            if(rs != null && rs.next()) {
                Address address = new Address();
                address.setCountry(rs.getString("a.country"));
                address.setCity(rs.getString("a.city"));
                address.setAddress(rs.getString("a.address"));
                address.setZip(rs.getInt("a.zip"));

                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("c.id"))
                        .firstName(rs.getString("c.firstName"))
                        .lastName(rs.getString("c.lastName"))
                        .dateOfBirth(rs.getDate("c.dateOfBirth"))
                        .address(address)
                        .placeOfWork(rs.getString("c.placeOfWork"));

                Contact contact = builder.build();
                contacts.add(contact);
            }
        } catch (SQLException e){

        } finally {
            closePreparedStatement(statement);
            closeResultSet(rs);
        }

        return contacts;
    }

    @Override
    public Contact getById(Long id) {
        return null;
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
