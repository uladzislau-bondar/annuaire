package command.helpers;


import util.MyStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AbstractHelper {
    protected HttpServletRequest request;

    public AbstractHelper(HttpServletRequest request){
        this.request = request;
    }

    public String getMethod(){
        return request.getMethod();
    }

    public Map<String, String> getQuery(){
        Map<String, String> query = new HashMap<>();
        try{
            query = MyStringUtils.splitQuery(request.getQueryString());
        } catch (UnsupportedEncodingException e){
            // todo msg
        }

        return query;
    }
}
