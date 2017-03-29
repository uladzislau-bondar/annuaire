package command;


import dto.ContactInfoDto;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactListService;
import util.MyStringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactListCommand.class);
    private ContactListService service;

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        service = new ContactListService();
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        Map<String, String> query = MyStringUtils.splitQuery(request.getQueryString());

        int offset = 0;
        if (query.get("offset") != null) {
            offset = Integer.valueOf(query.get("offset"));
        }

        switch (method) {
            case "GET":
                showContactList(offset);

                forward("index");
                break;
            case "POST":
                if (query.containsKey("method")) {
                    List<Long> ids = MyStringUtils.stringArrayToListOfLongs(request.getParameterValues("selected"));
                    if (query.get("method").equals("delete")) {
                        deleteSelectedContacts(ids);
                        redirect("/");

                    } else if (query.get("method").equals("email")) {
                        emailSelectedContacts(ids);
                        forward("email");
                    }
                }
                break;
        }
    }

    private void showContactList(int offset) {
        logger.info("Showing contacts");

        List<ContactInfoDto> contacts = service.getAllWithOffset(offset);
        request.setAttribute("contactList", contacts);
    }

    private void deleteSelectedContacts(List<Long> ids) {
        logger.info("Deleting selected contacts");

        service.deleteSelected(ids);
    }

    private void emailSelectedContacts(List<Long> ids) throws IOException {
        logger.info("Redirecting to email page with selected contacts");

        List<String> emails = service.getEmailsOfSelected(ids);

        //todo remove to helper
        String emailsList = String.join("; ", emails);
        logger.info(emailsList);

        ServletContext context = request.getServletContext();
        String templatePath = context.getRealPath("/WEB-INF/templates");
        StringTemplateGroup group = new StringTemplateGroup("email", templatePath);
        StringTemplate birthday = group.getInstanceOf("birthday");
        StringTemplate christmas = group.getInstanceOf("christmas");

        Map<String, String> templates = new HashMap<>();
        templates.put("birthday", birthday.getTemplate());
        templates.put("christmas", christmas.getTemplate());

        request.setAttribute("templates", templates);
        request.setAttribute("emails", emailsList);
    }

}
