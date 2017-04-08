package command.helpers;


import com.annuaire.service.TemplateService;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import com.annuaire.util.Utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ContactListHelper extends AbstractHelper{
    private static final String FAKE_LAST_NAME = "*ФАМИЛИЯ*";
    private static final String FAKE_FIRST_NAME = "*ИМЯ*";
    public ContactListHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public List<Long> getSelectedIds(){
        return Utils.toLongList(request.getParameterValues("selected"));
    }

    public void redirectToEmailPageWithList(List<String> emails){
        String emailsList = String.join("; ", emails);
        request.setAttribute("emails", emailsList);
        redirectToEmailPage();
    }

    public void redirectToEmailPage(){
        String templatesLocation = getTemplatesLocation();
        StringTemplateGroup templatesGroup = TemplateService.retrieveStringTemplateGroup(templatesLocation);
        Map<String, StringTemplate> templates = TemplateService.getTemplates(templatesGroup);

        for (StringTemplate t : templates.values()) {
            t.setAttribute("firstName", FAKE_FIRST_NAME);
            t.setAttribute("lastName", FAKE_LAST_NAME);
        }

        request.setAttribute("templates", TemplateService.getGeneratedTemplates(templates));
    }
}
