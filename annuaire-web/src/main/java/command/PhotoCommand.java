package command;


import com.annuaire.exceptions.ServiceException;
import command.helpers.PhotoHelper;
import org.apache.commons.lang3.StringUtils;
import com.annuaire.service.PhotoService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PhotoCommand extends AbstractCommand {
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
                processPhotoRendering(contactId);
            } else {
                throw new ServletException("Id is invalid.");
            }
        } else {
            throw new ServletException("No attachment id specified.");
        }
    }

    private void processPhotoRendering(Long id) throws ServletException, IOException {
        try {
            File photo = service.getByContactId(id);
            if (photo != null) {
                helper.renderPhoto(photo);
            } else {
                throw new ServletException("Id is invalid.");
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
