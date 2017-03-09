package command;


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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger log = LogManager.getLogger(ContactCommand.class);

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void process() throws ServletException, IOException{
        Address address1 = new Address();
        address1.setAddress("Kolasa 28, 612a");
        Address address2 = new Address();
        address2.setAddress("Hmelnockogo 94, 117");

        ContactBuilder builder1 = new ContactBuilder("Ulad", "Bondar")
                .id(123L)
                .dateOfBirth(new Date())
                .placeOfWork("BSUIR")
                .address(address1);
        Contact contact1 = builder1.build();
        ContactBuilder builder2 = new ContactBuilder("John", "Smith")
                .dateOfBirth(new Date(123456))
                .placeOfWork("American Aerlines")
                .address(address2);
        Contact contact2 = builder2.build();

        ContactDto dto1 = DtoUtils.convertToDto(contact1);
        ContactDto dto2 = DtoUtils.convertToDto(contact2);

        List<ContactDto> contactList = new ArrayList<>();
        contactList.add(dto1);
        contactList.add(dto2);

        request.setAttribute("contactList", contactList);

        forward("index");
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
