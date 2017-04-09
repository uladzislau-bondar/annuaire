package command.helpers;


import com.annuaire.dto.AttachmentDatabaseDto;
import com.annuaire.entities.Attachment;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AttachmentHelper extends AbstractHelper {
    public AttachmentHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void renderAttachment(AttachmentDatabaseDto attachment) throws ServletException, IOException {
        if (attachmentExists(attachment)){
            addDownloadParams(attachment.getName(), attachment.getFile());
            renderFile(attachment.getFile());
        } else{
            throw new ServletException("Attachment doesn't exist");
        }
    }

    private boolean attachmentExists(AttachmentDatabaseDto attachment){
        return attachment.getName() != null;
    }

    private void addDownloadParams(String fileName, File attachment) throws ServletException{
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(attachment.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) attachment.length());
        response.setCharacterEncoding("utf-8");

        String headerKey = "Content-Disposition";

        String headerValue = null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            headerValue = String.format("attachment; filename=\"%s\"", fileName);
        } catch (UnsupportedEncodingException e) {
            throw new ServletException(e);
        }
        response.setHeader(headerKey, headerValue);
    }
}
