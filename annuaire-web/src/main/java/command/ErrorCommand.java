package command;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorCommand extends AbstractCommand{
    public ErrorCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("requestUri", requestUri);
        request.setAttribute("servletName", servletName);

        if (throwable != null){
            request.setAttribute("exceptionName", throwable.getClass().getName());
            request.setAttribute("message", throwable.getMessage());
        }

        setTitle(statusCode.toString());
        forward("error");
    }
}
