package command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCommand implements Command {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public AbstractCommand(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public abstract void execute() throws ServletException, IOException;

    public abstract void process() throws ServletException, IOException;

    public void forward(String jspName) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jspName + ".jsp")
                .forward(request, response);
    }

    public void forwardWithMethod(String jspName, String methodName) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jspName + ".jsp" + "?method=" + methodName)
                .forward(request, response);
    }

    public void redirect(String path) throws IOException{
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + path));
    }

    public void redirectToErrorPage() throws IOException{
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/error"));
    }

    protected void setTitle(String title){
        request.setAttribute("title", title);
    }
}
