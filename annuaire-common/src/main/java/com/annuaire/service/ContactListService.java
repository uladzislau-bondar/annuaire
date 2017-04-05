package com.annuaire.service;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.Transaction;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.ContactInfoDto;
import com.annuaire.entities.Contact;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import com.annuaire.util.DtoUtils;
import com.annuaire.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactListService {
    public List<ContactInfoDto> getAllWithOffset(int offset) throws ServiceException{
        final List<ContactInfoDto> list = new ArrayList<>();

        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                int limit = 10;
                List<Contact> contacts = dao.getWithOffset(limit, offset);

                for (Contact contact : contacts) {
                    list.add(DtoUtils.convertToInfoDto(contact));
                }
            });
        } catch (TransactionException e){
            throw new ServiceException();
        }

        return list;
    }

    public void deleteSelected(List<Long> ids) throws ServiceException{
        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                for (Long id : ids) {
                    dao.delete(id);
                }
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }

    }

    public List<String> getEmailsOfSelected(List<Long> ids) throws ServiceException{
        final List<String> emails = new ArrayList<>();

        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                for (Long id : ids) {
                    emails.add(dao.getEmailById(id));
                }
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }

        return Utils.deleteNulls(emails);
    }

}
