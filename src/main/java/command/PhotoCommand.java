package command;


import command.helpers.PhotoHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class PhotoCommand extends AbstractCommand{
    private PhotoHelper helper;
    private PhotoService service;
    public PhotoCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        helper = new PhotoHelper(request, response);
        service = new PhotoService();
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();

        if (method.equals("GET")){
            processPhotoRendering();
        }
    }

    private void processPhotoRendering() throws ServletException, IOException {
        String idParam = helper.getQuery().get("id");
        if (StringUtils.isNotEmpty(idParam)){
            Long id = Long.valueOf(idParam);

            File photo = service.getByContactId(id);
            helper.renderPhoto(photo);
        }

    }
}
