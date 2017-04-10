package command.helpers;


import com.annuaire.service.TemplateService;
import org.apache.commons.io.IOUtils;
import com.annuaire.util.Utils;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    public Map<String, String> getQuery() throws ServletException{
        try{
            return Utils.splitQuery(request.getQueryString());
        } catch (UnsupportedEncodingException e){
            throw new ServletException(e);
        }
    }

    public String getMethodParam() throws ServletException{
        return getQuery().get("method");
    }

    public int getOffset() throws ServletException{
        String offsetParam = getQuery().get("offset");
        return offsetParam != null ? Integer.valueOf(offsetParam) : 0;
    }

    public void setAlertMessage(String message){
        HttpSession session = request.getSession();
        session.setAttribute("alertMessage", message);
    }

    public String getAlertMessage(){
        HttpSession session = request.getSession();
        return (String) session.getAttribute("alertMessage");
    }

    public void clearAlertMessage(){
        HttpSession session = request.getSession();
        session.setAttribute("alertMessage", null);
    }

    public String getTemplatesLocation(){
        return request.getServletContext().getRealPath("/WEB-INF/templates/template.stg");
    }

    public void redirectToEmailPage(){
        String templatesLocation = getTemplatesLocation();
        Map<String, ST> templates = TemplateService.getTemplates(templatesLocation);

        String fakeLastName = "*ФАМИЛИЯ*";
        String fakeFirstName = "*ИМЯ*";

        for (ST t : templates.values()) {
            t.add("firstName", fakeFirstName);
            t.add("lastName", fakeLastName);
        }

        request.setAttribute("templates", TemplateService.getGeneratedTemplates(templates));
    }

    protected void renderFile(File file) throws IOException{
        if (file != null) {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            IOUtils.copy(in, out);
        }
    }
}
