package command;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorCommand extends AbstractCommand{
    private static final Logger logger = LogManager.getLogger(ErrorCommand.class);
    public ErrorCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("requestUri", requestUri);

        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (throwable != null){
            String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
            request.setAttribute("exception", throwable.getClass().getName());
            request.setAttribute("message", message);
        }

        setTitle(statusCode.toString());
        forward("error");
    }
}
