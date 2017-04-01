package service;


import dao.ContactDao;
import db.TransactionHandler;
import dto.ContactInfoDto;
import entities.Contact;
import util.DtoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {
    public List<ContactInfoDto> getSearchResult(Map<String, String> params, int offset) {
        final List<ContactInfoDto> list = new ArrayList<>();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            // todo maybe change limit
            int limit = 10;
            List<Contact> contacts = dao.getByWithOffset(params, limit, offset);

            for (Contact contact : contacts) {
                list.add(DtoUtils.convertToInfoDto(contact));
            }
        });

        return list;
    }
}
