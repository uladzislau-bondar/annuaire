package command;


import dao.AddressDao;
import dao.ContactDao;
import dao.PhoneDao;
import dto.ContactDto;
import entities.Address;
import entities.Contact;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DtoUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
        super.forward("index");
    }

    @Override
    public void process() throws ServletException, IOException {
        int offset = 0;
        if (request.getParameter("offset") != null) {
            offset = Integer.valueOf(request.getParameter("offset"));
        }

        ContactDao contactDao = new ContactDao();
        List<Contact> contactList = contactDao.getWithOffset(10, offset);

        AddressDao addressDao = new AddressDao();
        PhoneDao phoneDao = new PhoneDao();
        for (Contact contact : contactList) {
            Address address = addressDao.getByContactId(contact.getId());
            contact.setAddress(address);
        }

        List<ContactDto> contactDtoList = new ArrayList<>();

        for (Contact c : contactList) {
            contactDtoList.add(DtoUtils.convertToDto(c));
        }

        request.setAttribute("contactList", contactDtoList);
    }
}
