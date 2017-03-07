package servlets;

import command.ContactListCommand;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet({"/","/contacts"})
public class ContactListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ContactListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ContactBuilder builder = new ContactBuilder("Ulad", "Bondar")
                .email("colinforzeal@yandex.ru");
        Contact contact = builder.build();
        request.setAttribute("contact", contact);

        ContactListCommand contactListCommand = new ContactListCommand(request, response);
        contactListCommand.forward("index");
    }
}