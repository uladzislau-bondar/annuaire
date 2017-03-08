package command;


import entities.Contact;
import entities.ContactBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactListCommand extends Command {

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void process() {
        ContactBuilder builder1 = new ContactBuilder("Ulad", "Bondar")
                .dateOfBirth(new Date())
                .placeOfWork("BSUIR");
        Contact contact1 = builder1.build();
        ContactBuilder builder2 = new ContactBuilder("John", "Smith")
                .dateOfBirth(new Date(123456))
                .placeOfWork("American Aerlines");
        Contact contact2 = builder2.build();
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact1);
        contactList.add(contact2);

        request.setAttribute("contactList", contactList);
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
