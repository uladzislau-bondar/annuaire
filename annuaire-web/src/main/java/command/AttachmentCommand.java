package command;

import com.annuaire.exceptions.ServiceException;
import com.annuaire.service.AttachmentService;
import command.helpers.AttachmentHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AttachmentCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(AttachmentCommand.class);
    private AttachmentHelper helper;
    private AttachmentService service;

    public AttachmentCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        helper = new AttachmentHelper(request, response);
        service = new AttachmentService();
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();

        if (method.equals("GET")) {
            processGet();
        } else {
            throw new ServletException("Can't process" + method);
        }
    }

    private void processGet() throws ServletException, IOException {
        Map<String, String> params = helper.getQuery();

        if (params.containsKey("id")) {
            Long id = Long.valueOf(params.get("id"));
            processAttachmentRendering(id);
        } else{
            throw new ServletException("No attachment id specified.");
        }
    }

    private void processAttachmentRendering(Long id) throws ServletException, IOException {
        logger.info("Rendering attachment {}", id);

        try {
            File photo = service.getByContactId(id);
            helper.renderAttachment(photo);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
