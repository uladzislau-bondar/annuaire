package command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public Command(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    protected abstract void process();

    protected void forward(String jspName)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jspName + ".jsp")
                .forward(request, response);
    }
}
