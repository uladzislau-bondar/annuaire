package command.helpers;


import com.annuaire.service.TemplateService;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class EmailHelper extends AbstractHelper{
    public EmailHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public Map<String, String> getMessageParams(){
        Map<String, String> params = new HashMap<>();
        params.put("subject", request.getParameter("subject"));
        params.put("message", request.getParameter("message"));
        params.put("emails", request.getParameter("emails"));
        params.put("template", request.getParameter("template"));
        params.put("templatesLocation", getTemplatesLocation());

        return params;
    }
}
