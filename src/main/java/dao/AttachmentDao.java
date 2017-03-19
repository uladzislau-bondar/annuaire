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
        return null;
    }

    @Override
    public void update(Attachment attachment) {

    }

    @Override
    public void delete(Long id) {

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
}
