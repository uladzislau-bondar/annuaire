package db.constants;


public interface ContactConstants {
    String SAVE = "INSERT INTO contacts (firstName, lastName, middleName," +
            "dateOfBirth, sex, citizenship, maritalStatus, website, email, placeOfWork, photoPath)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String GET_ALL = "SELECT * FROM contacts";
    String GET_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    String GET_EMAIL_BY_ID = "SELECT email FROM contacts WHERE id = ?";
    String GET_WITH_OFFSET = "SELECT * FROM contacts LIMIT ? OFFSET ?";
    String UPDATE = "UPDATE contacts SET firstName = ?, lastName = ?, middleName = ?," +
            "dateOfBirth = ?, sex = ?, citizenship = ?, maritalStatus = ?, website = ?, email = ?, placeOfWork = ?," +
            "photoPath = ? WHERE id = ?";
    String DELETE = "DELETE FROM contacts WHERE id = ?";
}
