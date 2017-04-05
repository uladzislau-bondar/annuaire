package command.helpers;


import org.antlr.stringtemplate.StringTemplateGroup;
import com.annuaire.util.Utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ContactListHelper extends AbstractHelper{
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
        StringTemplateGroup templatesGroup = retrieveStringTemplateGroup();
        Map<String, String> templates = createMapOfTemplates(templatesGroup);

        request.setAttribute("templates", templates);
    }

    private StringTemplateGroup retrieveStringTemplateGroup(){
        ServletContext context = request.getServletContext();
        String templatePath = context.getRealPath("/WEB-INF/templates");
        return new StringTemplateGroup("email", templatePath);
    }

    private Map<String, String> createMapOfTemplates(StringTemplateGroup group){
        Map<String, String> templates = new HashMap<>();
        templates.put("birthday", group.getInstanceOf("birthday").getTemplate());
        templates.put("christmas", group.getInstanceOf("christmas").getTemplate());

        return templates;
    }
}
