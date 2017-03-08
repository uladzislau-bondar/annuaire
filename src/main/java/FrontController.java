import command.Command;
import command.ContactListCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/app/*")
public class FrontController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo path looks odd
        String uri = req.getRequestURI().substring(4);

        Command command;
        switch (uri){
            case "/":
                command = new ContactListCommand(req, resp);
                command.process();
                command.forward("index");
                break;
        }
    }


}
