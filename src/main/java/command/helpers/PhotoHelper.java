package command.helpers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PhotoHelper extends AbstractHelper{
    private HttpServletResponse response;

    public PhotoHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        this.response = response;
    }

    public void renderPhoto(File photo){
        if (photo != null){
            try{
                FileInputStream in = new FileInputStream(photo);
                OutputStream out = response.getOutputStream();

                byte[] buf = new byte[1024];
                int count = 0;
                while ((count = in.read(buf)) >= 0) {
                    out.write(buf, 0, count);
                }
                out.close();
                in.close();
            } catch (IOException e){
                // todo msg
            }

        }
    }
}
