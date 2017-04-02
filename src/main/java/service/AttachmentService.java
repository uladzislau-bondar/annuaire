package service;


import dao.AttachmentDao;
import db.TransactionHandler;

import java.io.File;

public class AttachmentService {
    public File getByContactId(Long id){
        return new File(getFilePath(id));
    }

    private String getFilePath(Long id){
        final StringBuilder filePath = new StringBuilder();
        TransactionHandler.run(connection -> {
            AttachmentDao dao = new AttachmentDao(connection);
            filePath.append(dao.getFilePathById(id));
        });

        return filePath.toString();
    }
}
