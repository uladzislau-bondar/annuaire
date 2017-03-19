package dao;


import db.constants.AttachmentConstants;
import entities.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DaoUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
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
}
