package db.constants;


public interface AddressConstants {
    String SAVE = "INSERT INTO addresses (contactId, country, city, address, zip)" +
            " VALUES (?, ?, ?, ?, ?)";
    String GET_ALL = "SELECT * FROM addresses";
    String GET_BY_ID = "SELECT * FROM addresses WHERE id = ?";
    String GET_BY_CONTACT_ID = "SELECT * FROM addresses WHERE contactId = ?";
    String UPDATE = "UPDATE addresses SET contactId = ?, country = ?, city = ?," +
            "address = ?, zip = ? WHERE id = ?";
    String DELETE = "DELETE FROM addresses WHERE id = ?";
}
