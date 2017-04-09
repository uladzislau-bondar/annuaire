package com.annuaire.dao;


import com.annuaire.db.constants.AttachmentConstants;
import com.annuaire.entities.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDao extends AbstractTemplateDao<Attachment, Long> {
    private final static Logger logger = LogManager.getLogger(AttachmentDao.class);

    public AttachmentDao(Connection connection) {
        super(connection);
    }

    @Override
    public Long save(Attachment attachment) throws SQLException {
        PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(AttachmentConstants.SAVE);
        statement.setLong(1, attachment.getContactId());
        statement.setString(2, attachment.getName());
        statement.setDate(3, attachment.getDateOfUpload());
        statement.setString(4, attachment.getComment());
        String filePath = attachment.getFileName();
        statement.setString(5, filePath);

        logger.info(statement.toString());

        statement.executeUpdate();

        return obtainIdFromStatement(statement);
    }

    @Override
    public List<Attachment> getAll() throws SQLException {
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_ALL);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseListFromResultSet(set);
    }

    @Override
    public Attachment getById(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseAttachmentFromResultSet(set);
    }

    @Override
    public void update(Attachment attachment) throws SQLException {
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.UPDATE);
        statement.setLong(1, attachment.getContactId());
        statement.setString(2, attachment.getName());
        statement.setDate(3, attachment.getDateOfUpload());
        statement.setString(4, attachment.getComment());
        String filePath = attachment.getFileName();
        statement.setString(5, filePath);
        statement.setLong(6, attachment.getId());

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.DELETE);
        statement.setLong(1, id);

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    public List<Attachment> getByContactId(Long contactId) throws SQLException {
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_BY_CONTACT_ID);
        statement.setLong(1, contactId);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();

        return parseListFromResultSet(set);
    }

    public String getFilePathById(Long id) throws SQLException {
        String email = null;

        PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_FILEPATH_BY_ID);
        statement.setLong(1, id);

        logger.info(statement.toString());

        ResultSet set = statement.executeQuery();
        if (set.next()) {
            email = set.getString("filePath");
        }

        return email;
    }

    public void updateFilePathById(String filePath, Long id) throws SQLException{
        PreparedStatement statement = getPreparedStatement(AttachmentConstants.UPDATE_FILEPATH);
        statement.setString(1, filePath);
        statement.setLong(2, id);

        logger.info(statement.toString());

        statement.executeUpdate();
    }

    private List<Attachment> parseListFromResultSet(ResultSet set) throws SQLException {
        List<Attachment> attachments = new ArrayList<>();

        while (set.next()) {
            Attachment attachment = new Attachment();
            attachment.setId(set.getLong("id"));
            attachment.setContactId(set.getLong("contactId"));
            attachment.setName(set.getString("name"));
            attachment.setDateOfUpload(set.getDate("dateOfUpload"));
            attachment.setComment(set.getString("comment"));
            attachment.setFileName(set.getString("filePath"));
            attachments.add(attachment);
        }

        return attachments;
    }

    private Attachment parseAttachmentFromResultSet(ResultSet set) throws SQLException {
        Attachment attachment = null;

        if (set.next()) {
            attachment = new Attachment();
            attachment.setId(set.getLong("id"));
            attachment.setContactId(set.getLong("contactId"));
            attachment.setName(set.getString("name"));
            attachment.setDateOfUpload(set.getDate("dateOfUpload"));
            attachment.setComment(set.getString("comment"));
            attachment.setFileName(set.getString("filePath"));
        }

        return attachment;
    }

}
