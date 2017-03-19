package dao;


import db.constants.AttachmentConstants;
import entities.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DaoUtils;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDao extends AbstractTemplateDao<Attachment, Long> {
    private final static Logger logger = LogManager.getLogger(AttachmentDao.class);

    @Override
    public Long save(Attachment attachment) {
        Long id = null;

        try (PreparedStatement statement = getPreparedStatementAndReturnGeneratedKeys(AttachmentConstants.SAVE)) {
            statement.setLong(1, attachment.getContactId());
            statement.setString(2, attachment.getName());
            statement.setDate(3, attachment.getDateOfUpload());
            statement.setString(4, attachment.getComment());
            String filePath = DaoUtils.fileToPath(attachment.getFile());
            statement.setString(5, filePath);

            logger.info(statement.toString());

            statement.executeUpdate();
            id = obtainIdFromStatement(statement);
        } catch (SQLException e) {
            logger.error(e);
        }

        return id;
    }

    @Override
    public List<Attachment> getAll() {
        List<Attachment> attachments = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_ALL)) {
            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();
            attachments = fillListFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return attachments;
    }

    @Override
    public Attachment getById(Long id) {
        Attachment attachment = null;

        try (PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_BY_ID)) {
            statement.setLong(1, id);

            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();

            attachment = fillAttachmentFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return attachment;
    }

    @Override
    public void update(Attachment attachment) {
        try (PreparedStatement statement = getPreparedStatement(AttachmentConstants.UPDATE)) {
            statement.setLong(1, attachment.getContactId());
            statement.setString(2, attachment.getName());
            statement.setDate(3, attachment.getDateOfUpload());
            statement.setString(4, attachment.getComment());
            String filePath = DaoUtils.fileToPath(attachment.getFile());
            statement.setString(5, filePath);
            statement.setLong(6, attachment.getId());

            logger.info(statement.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement statement = getPreparedStatement(AttachmentConstants.DELETE)) {
            statement.setLong(1, id);

            logger.info(statement.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public List<Attachment> getByContactId(Long contactId){
        List<Attachment> attachments = new ArrayList<>();

        try (PreparedStatement statement = getPreparedStatement(AttachmentConstants.GET_BY_CONTACT_ID)) {
            statement.setLong(1, contactId);

            logger.info(statement.toString());

            ResultSet set = statement.executeQuery();

            attachments = fillListFromResultSet(set);
        } catch (SQLException e) {
            logger.error(e);
        }

        return attachments;
    }

    private List<Attachment> fillListFromResultSet(ResultSet set) throws SQLException {
        List<Attachment> attachments = new ArrayList<>();

        while (set.next()) {
            Attachment attachment = new Attachment();
            attachment.setId(set.getLong("id"));
            attachment.setContactId(set.getLong("contactId"));
            attachment.setName(set.getString("name"));
            attachment.setDateOfUpload(set.getDate("dateOfUpload"));
            attachment.setComment(set.getString("comment"));
            File file = DaoUtils.pathToFile(set.getString("filePath"));
            attachment.setFile(file);

            attachments.add(attachment);
        }

        return attachments;
    }

    private Attachment fillAttachmentFromResultSet(ResultSet set) throws SQLException {
        Attachment attachment = new Attachment();

        if (set.next()) {
            attachment.setId(set.getLong("id"));
            attachment.setContactId(set.getLong("contactId"));
            attachment.setName(set.getString("name"));
            attachment.setDateOfUpload(set.getDate("dateOfUpload"));
            attachment.setComment(set.getString("comment"));
            File file = DaoUtils.pathToFile(set.getString("filePath"));
            attachment.setFile(file);
        }

        return attachment;
    }
}
