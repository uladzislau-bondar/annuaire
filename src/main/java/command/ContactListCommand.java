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
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        Map<String, String> query = StringUtils.splitQuery(request.getQueryString());

        int offset = 0;
        if (query.get("offset") != null) {
            offset = Integer.valueOf(query.get("offset"));
        }

        switch (method){
            case "GET":
                showContactList(offset);

                forward("index");
                break;
            case "POST":
                if (query.containsKey("method")){
                    if (query.get("method").equals("delete")){
                        List<Long> ids = StringUtils.valuesOf(request.getParameterValues("selected"));
                        deleteSelectedContacts(ids);
                    }
                }

                redirect("/");
                break;
        }
    }

    private void showContactList(int offset){
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

    private void deleteSelectedContacts(List<Long> ids) {
        ContactDao contactDao = new ContactDao();
        for (Long id: ids){
            contactDao.delete(id);

            logger.info("Deleting contact #" + id);
        }
    }

}
