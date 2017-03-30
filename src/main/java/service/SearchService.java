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
    public List<ContactInfoDto> getSearchResult(Map<String, String> params) {
        final List<ContactInfoDto> list = new ArrayList<>();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            List<Contact> contacts = dao.getBy(params);

            for (Contact contact : contacts) {
                list.add(DtoUtils.convertToInfoDto(contact));
            }
        });

        return list;
    }
}
