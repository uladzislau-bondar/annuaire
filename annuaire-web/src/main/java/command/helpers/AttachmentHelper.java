package command.helpers;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class AttachmentHelper extends AbstractHelper {
    public AttachmentHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void renderAttachment(File attachment) {
        renderFile(attachment);
        addDownloadParams(attachment);
    }

    private void addDownloadParams(File attachment){
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(attachment.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) attachment.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", attachment.getName());
        response.setHeader(headerKey, headerValue);
    }
}
