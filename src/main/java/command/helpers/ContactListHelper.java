package command.helpers;


import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ContactListHelper extends AbstractHelper{
    public ContactListHelper(HttpServletRequest request) {
        super(request);
    }

    public int getOffset(){
        String offsetParam = getQuery().get("offset");
        return offsetParam != null ? Integer.valueOf(offsetParam) : 0;
    }

    public List<Long> getSelectedIds(){
        String[] selected = request.getParameterValues("selected");
        return Arrays.stream(selected).map(Long::valueOf).collect(Collectors.toList());
    }

    public void processRedirectionToEmailPage(List<String> emails){
        String emailsList = String.join("; ", emails);
        StringTemplateGroup templatesGroup = retrieveStringTemplateGroup();
        Map<String, String> templates = createMapOfTemplates(templatesGroup);

        request.setAttribute("templates", templates);
        request.setAttribute("emails", emailsList);
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
