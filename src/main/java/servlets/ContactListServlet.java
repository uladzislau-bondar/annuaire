package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet({"/","/contacts"})
public class ContactListServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ContactListServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("name", "Ulad");
        logger.info(request.getAttribute("name" + "is inserted"));
        request.getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }
}


//todo add jstl library