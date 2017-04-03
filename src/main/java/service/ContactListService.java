package service;


import dao.ContactDao;
import db.TransactionHandler;
import dto.ContactInfoDto;
import entities.Contact;
import util.DtoUtils;

import java.util.ArrayList;
import java.util.List;

public class ContactListService {
    public List<ContactInfoDto> getAllWithOffset(int offset){
        final List<ContactInfoDto> list = new ArrayList<>();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            int limit = 10;
            List<Contact> contacts = dao.getWithOffset(limit, offset);

            for (Contact contact : contacts) {
                list.add(DtoUtils.convertToInfoDto(contact));
            }
        });

        return list;
    }

    public void deleteSelected(List<Long> ids){
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            for (Long id : ids) {
                dao.delete(id);
            }
        });
    }

    public List<String> getEmailsOfSelected(List<Long> ids){
        final List<String> emails = new ArrayList<>();
        TransactionHandler.run(connection -> {
            ContactDao dao = new ContactDao(connection);
            for (Long id : ids) {
                emails.add(dao.getEmailById(id));
            }
        });

        return emails;
    }

}
