package com.annuaire.service;

import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;

import java.io.File;

public class PhotoService {
    public File getByContactId(Long id){
        return new File(getFilePath(id));
    }

    private String getFilePath(Long id){
        final StringBuilder filePath = new StringBuilder();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            filePath.append(dao.getPhotoPathById(id));
        });

        return filePath.toString();
    }
}
