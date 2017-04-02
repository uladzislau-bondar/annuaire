package command;

import command.helpers.AbstractHelper;
import command.helpers.AttachmentHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AttachmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class AttachmentCommand extends AbstractCommand{
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

        if (method.equals("GET")){
            processAttachmentRendering();
        }
    }

    private void processAttachmentRendering() throws ServletException, IOException{
        String idParam = helper.getQuery().get("id");
        if (StringUtils.isNotEmpty(idParam)){
            Long id = Long.valueOf(idParam);
            File photo = service.getByContactId(id);
            System.out.println(photo.getAbsolutePath());
            helper.renderAttachment(photo);
        }
    }
}
