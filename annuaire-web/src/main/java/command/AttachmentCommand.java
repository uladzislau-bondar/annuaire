package command;

import com.annuaire.dto.AttachmentDatabaseDto;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.service.AttachmentService;
import command.helpers.AttachmentHelper;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            String idParam = params.get("id");
            if (NumberUtils.toInt(idParam) != 0) {
                Long contactId = Long.valueOf(idParam);
                processAttachmentRendering(contactId);
            } else {
                throw new ServletException("Id is invalid.");
            }
        } else {
            throw new ServletException("No attachment id specified.");
        }
    }

    private void processAttachmentRendering(Long id) throws ServletException, IOException {
        logger.info("Rendering attachment {}", id);

        try {
            AttachmentDatabaseDto attachment = service.getByContactId(id);
            helper.renderAttachment(attachment);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
