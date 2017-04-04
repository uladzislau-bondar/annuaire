package com.annuaire.service;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.ContactInfoDto;
import com.annuaire.entities.Contact;
import com.annuaire.util.DtoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {
    public List<ContactInfoDto> getSearchResult(Map<String, String> params, int offset) {
        final List<ContactInfoDto> list = new ArrayList<>();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            int limit = 10;
            List<Contact> contacts = dao.getByWithOffset(params, limit, offset);

            for (Contact contact : contacts) {
                list.add(DtoUtils.convertToInfoDto(contact));
            }
        });

        return list;
    }
}
