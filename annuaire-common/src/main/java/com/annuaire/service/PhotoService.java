package com.annuaire.service;

import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;

import java.io.File;

public class PhotoService {
    public File getByContactId(Long id) throws ServiceException {
        return new File(getFilePath(id));
    }

    private String getFilePath(Long id) throws ServiceException {
        final StringBuilder filePath = new StringBuilder();
        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                filePath.append(dao.getPhotoPathById(id));
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }


        return filePath.toString();
    }
}
