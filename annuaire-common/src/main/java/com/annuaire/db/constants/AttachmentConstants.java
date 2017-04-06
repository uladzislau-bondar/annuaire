package com.annuaire.db.constants;


public interface AttachmentConstants {
    String SAVE = "INSERT INTO attachments (contactId, name, dateOfUpload, comment, filePath)" +
            " VALUES (?, ?, ?, ?, ?)";
    String GET_ALL = "SELECT * FROM attachments";
    String GET_BY_ID = "SELECT * FROM attachments WHERE id = ?";
    String GET_FILEPATH_BY_ID = "SELECT filePath FROM attachments WHERE id = ?";
    String GET_BY_CONTACT_ID = "SELECT * FROM attachments WHERE contactId = ?";
    String UPDATE = "UPDATE attachments SET contactId = ?, name = ?, dateOfUpload = ?," +
            "comment = ?, filePath = ? WHERE id = ?";
    String UPDATE_FILEPATH = "UPDATE attachments SET filePath = ? WHERE id = ?";
    String DELETE = "DELETE FROM attachments WHERE id = ?";
}
