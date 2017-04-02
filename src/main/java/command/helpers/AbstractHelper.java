package command.helpers;


import org.apache.commons.io.IOUtils;
import util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AbstractHelper {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public AbstractHelper(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public String getMethod(){
        return request.getMethod();
    }

    public Map<String, String> getQuery(){
        Map<String, String> query = new HashMap<>();
        try{
            query = Utils.splitQuery(request.getQueryString());
        } catch (UnsupportedEncodingException e){
            // todo msg
        }

        return query;
    }

    public String getMethodParam(){
        return getQuery().get("method");
    }

    public int getOffset(){
        String offsetParam = getQuery().get("offset");
        return offsetParam != null ? Integer.valueOf(offsetParam) : 0;
    }

    public void renderFile(File file){
        System.out.println(file.getAbsolutePath());
        if (file != null) {
            try {
                FileInputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream();

                IOUtils.copy(in, out);
            } catch (IOException e) {
                // todo msg
            }

        }
    }
}
