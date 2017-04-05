package command.helpers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PhotoHelper extends AbstractHelper{
    public PhotoHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void renderPhoto(File photo) throws IOException{
        renderFile(photo);
    }
}
