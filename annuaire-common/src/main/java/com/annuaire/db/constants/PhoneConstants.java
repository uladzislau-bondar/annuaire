package com.annuaire.db.constants;


public interface PhoneConstants {
    String SAVE = "INSERT INTO phones (contactId, countryCode, operatorCode, number, type, comment)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    String GET_ALL = "SELECT * FROM phones";
    String GET_BY_ID = "SELECT * FROM phones WHERE id = ?";
    String GET_BY_CONTACT_ID = "SELECT * FROM phones WHERE contactId = ?";
    String UPDATE = "UPDATE phones SET contactId = ?, countryCode = ?, operatorCode = ?, number = ?," +
            "type = ?, comment = ? WHERE id = ?";
    String DELETE = "DELETE FROM phones WHERE id = ?";
}
