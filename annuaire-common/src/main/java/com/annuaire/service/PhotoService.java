package com.annuaire.service;

import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class PhotoService {
    public File getByContactId(Long id) throws ServiceException {
        if (StringUtils.isNotEmpty(getFilePath(id))){
            return new File(getFilePath(id));
        } else {
            return null;
        }
    }

    private String getFilePath(Long id) throws ServiceException {
        final StringBuilder filePath = new StringBuilder();
        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                String photoPath = dao.getPhotoPathById(id);
                if (photoPath != null){
                    filePath.append(photoPath);
                }
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }

        return filePath.toString();
    }
}
