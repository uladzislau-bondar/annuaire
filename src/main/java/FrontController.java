import command.Command;
import command.ContactCommand;
import command.ContactListCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/*")
public class FrontController extends HttpServlet{
    private final static Logger log = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String path = req.getPathInfo();
        String queryString = req.getQueryString();
        log.info(path);
        log.info(queryString);

        Command command;
        //todo command factory
        if (path.equals("/")){
            command = new ContactListCommand(req, resp);
            command.process();
            command.forward("index");
        }
        else if (path.equals("/contacts") && queryString.contains("id=123")){
            command = new ContactCommand(req, resp);
            command.process();
            command.forward("contact");
        }
    }
}
