package com.annuaire.service;

import com.annuaire.dao.AttachmentDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;

import javax.servlet.ServletException;
import java.io.File;

public class AttachmentService {
    public File getByContactId(Long id) throws ServiceException{
        return new File(getFilePath(id));
    }

    private String getFilePath(Long id) throws ServiceException {
        final StringBuilder filePath = new StringBuilder();
        try {
            TransactionHandler.run(connection -> {
                AttachmentDao dao = new AttachmentDao(connection);
                filePath.append(dao.getFilePathById(id));
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }

        return filePath.toString();
    }
}
