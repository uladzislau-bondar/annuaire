package servlets;

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

        ContactBuilder builder = new ContactBuilder(123L, "Ulad", "Bondar")
                .email("colinforzeal@yandex.ru");
        Contact contact = builder.build();
        request.setAttribute("contact", contact);

        logger.info(request.getAttribute(contact.getFirstName() + " " + contact.getLastName() + " is inserted"));
        request.getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }
}