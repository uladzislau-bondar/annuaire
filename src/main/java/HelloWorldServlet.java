import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by colinforzeal on 6.3.17.
 */
@WebServlet("/")
public class HelloWorldServlet extends HttpServlet {
    //private final Logger logger = Logger.getLogger(HelloWorldServlet.class);
    //todo add log4j

    public void init() throws ServletException {
        //logger.info("Hello World initialized");
    }

    public void destroy() {
        //logger.info("Hello World destroyed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1> Hello world </h1>");
        //logger.info("Hello World doGet()");
    }
}
