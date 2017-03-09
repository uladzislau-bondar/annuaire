package command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ContactCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger(ContactCommand.class);

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void process() {
        log.info("got to contact");
        String id = request.getParameter("id");
        log.info(id);
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
