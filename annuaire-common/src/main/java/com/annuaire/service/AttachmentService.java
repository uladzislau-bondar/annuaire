package com.annuaire.service;

import com.annuaire.dao.AttachmentDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.AttachmentDatabaseDto;
import com.annuaire.entities.Attachment;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;

import javax.servlet.ServletException;
import java.io.File;

public class AttachmentService {
    public AttachmentDatabaseDto getByContactId(Long id) throws ServiceException {
        final AttachmentDatabaseDto dto = new AttachmentDatabaseDto();
        try {
            TransactionHandler.run(connection -> {
                AttachmentDao dao = new AttachmentDao(connection);
                Attachment attachment = dao.getById(id);
                if (attachment != null){
                    dto.setFile(new File(attachment.getFileName()));
                    dto.setName(attachment.getName());
                }
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }

        return dto;
    }
}
