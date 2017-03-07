package command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {
    protected void forward(HttpServletRequest request, HttpServletResponse response, String jspName)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jspName + ".jsp")
                .forward(request, response);
    }
}
