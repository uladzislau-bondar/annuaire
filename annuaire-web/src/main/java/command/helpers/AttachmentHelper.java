package command.helpers;


import com.annuaire.dto.AttachmentDatabaseDto;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class AttachmentHelper extends AbstractHelper {
    public AttachmentHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void renderAttachment(AttachmentDatabaseDto attachment) throws IOException {
        addDownloadParams(attachment.getName(), attachment.getFile());
        renderFile(attachment.getFile());
    }

    private void addDownloadParams(String fileName, File attachment) {
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(attachment.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) attachment.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);
    }
}
