package command;


import entities.Contact;
import entities.ContactBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContactListCommand extends Command {

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void process() {
        ContactBuilder builder = new ContactBuilder("Ulad", "Bondar")
                .email("colinforzeal@yandex.ru");
        Contact contact = builder.build();
        request.setAttribute("contact", contact);
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
