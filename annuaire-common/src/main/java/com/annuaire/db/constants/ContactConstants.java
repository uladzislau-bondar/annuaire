package com.annuaire.db.constants;


public interface ContactConstants {
    String SAVE = "INSERT INTO contacts (firstName, lastName, middleName," +
            "dateOfBirth, sex, citizenship, maritalStatus, website, email, placeOfWork, photoPath, " +
            "country, city, address, zip) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?)";
    String GET_ALL = "SELECT * FROM contacts";
    String GET_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    String GET_BY = "SELECT * FROM contacts WHERE ";
    String GET_EMAIL_BY_ID = "SELECT email FROM contacts WHERE id = ?";
    String GET_PHOTOPATH_BY_ID = "SELECT photoPath FROM contacts WHERE id = ?";
    String GET_EMAILS_BY_DATE_OF_BIRTH = "SELECT email FROM contacts WHERE dateOfBirth = ?";
    String GET_WITH_OFFSET = "SELECT * FROM contacts LIMIT ? OFFSET ?";
    String UPDATE = "UPDATE contacts SET firstName = ?, lastName = ?, middleName = ?," +
            "dateOfBirth = ?, sex = ?, citizenship = ?, maritalStatus = ?, website = ?, email = ?, placeOfWork = ?," +
            "photoPath = ?, country = ?, city = ?, address = ?, zip = ? WHERE id = ?";
    String UPDATE_PHOTOPATH = "UPDATE contacts SET photoPath = ? WHERE id = ?";
    String DELETE = "DELETE FROM contacts WHERE id = ?";
}
