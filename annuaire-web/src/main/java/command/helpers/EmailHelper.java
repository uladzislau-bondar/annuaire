package command.helpers;


import com.annuaire.service.TemplateService;
import org.antlr.stringtemplate.StringTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class EmailHelper extends AbstractHelper{
    private static final String FAKE_LAST_NAME = "*ФАМИЛИЯ*";
    private static final String FAKE_FIRST_NAME = "*ИМЯ*";
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

    public void redirectToEmailPage(){
        String templatesLocation = getTemplatesLocation();
        Map<String, StringTemplate> templates = TemplateService.getTemplates(templatesLocation);

        for (StringTemplate t : templates.values()) {
            t.setAttribute("firstName", FAKE_FIRST_NAME);
            t.setAttribute("lastName", FAKE_LAST_NAME);
        }

        request.setAttribute("templates", TemplateService.getGeneratedTemplates(templates));
    }
}
