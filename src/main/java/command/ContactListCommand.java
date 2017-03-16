package command;


import dao.ContactDao;
import db.Connector;
import dto.ContactDto;
import entities.Address;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DtoUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactCommand.class);

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
        forward("index");
    }

    @Override
    public void process() throws ServletException, IOException {
        ContactDao contactDao = new ContactDao();
        List<Contact> contactList = contactDao.getAll();
        List<ContactDto> contactDtoList = new ArrayList<>();

        for (Contact c: contactList) {
            contactDtoList.add(DtoUtils.convertToDto(c));
        }

        request.setAttribute("contactList", contactDtoList);
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
