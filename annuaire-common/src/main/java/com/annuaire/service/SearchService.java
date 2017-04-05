package com.annuaire.service;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.ContactInfoDto;
import com.annuaire.entities.Contact;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import com.annuaire.util.DtoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {
    public List<ContactInfoDto> getSearchResult(Map<String, String> params, int offset) throws ServiceException{
        final List<ContactInfoDto> list = new ArrayList<>();

        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                int limit = 10;
                List<Contact> contacts = dao.getByWithOffset(params, limit, offset);

                for (Contact contact : contacts) {
                    list.add(DtoUtils.convertToInfoDto(contact));
                }
            });
        } catch (TransactionException e){
            throw new ServiceException(e);
        }

        return list;
    }
}
