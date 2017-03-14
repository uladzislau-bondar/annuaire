package dao;


import entities.Contact;
import entities.ContactBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends AbstractTemplateDao<Contact, Long> {
    private final String SQL_GET_ALL = "SElECT * FROM contacts LIMIT 1";
    private final String SQL_GET_BY_ID = "SELECT * FROM contacts WHERE id = ?";

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
                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .dateOfBirth(rs.getDate("dateOfBirth"))
                        .placeOfWork(rs.getString("placeOfWork"));

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
        PreparedStatement statement = getPreparedStatement(SQL_GET_BY_ID);
        ResultSet rs = null;
        Contact contact = null;

        try{
            statement.setLong(1, id);
            rs = getResultSet(statement);

            if (rs != null && rs.next()){
                ContactBuilder builder = new ContactBuilder();
                builder.id(rs.getLong("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .dateOfBirth(rs.getDate("dateOfBirth"))
                        .placeOfWork(rs.getString("placeOfWork"));

                contact = builder.build();
            }
        } catch (SQLException e){

        } finally {
            closePreparedStatement(statement);
            closeResultSet(rs);
        }

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
