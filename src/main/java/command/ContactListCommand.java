package command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContactListCommand extends Command {
    @Override
    public void forward(HttpServletRequest request, HttpServletResponse response, String jspName) throws ServletException, IOException {
        super.forward(request, response, jspName);
    }
}
