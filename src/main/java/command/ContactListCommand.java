package command;


import command.helpers.ContactListHelper;
import dto.ContactInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactListService;
import util.MyStringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactListCommand.class);
    private ContactListService service;
    private ContactListHelper helper;

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        service = new ContactListService();
        helper = new ContactListHelper(request);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();

        switch (method) {
            case "GET":
                showContactList();
                forward("index");
                break;
            case "POST":
                Map<String, String> query = helper.getQuery();
                if (query.containsKey("method")) {
                    if (query.get("method").equals("delete")) {
                        deleteSelectedContacts();
                        redirect("/");
                    } else if (query.get("method").equals("email")) {
                        emailSelectedContacts();
                        forward("email");
                    }
                }
                break;
        }
    }

    private void showContactList() {
        logger.info("Showing contacts");

        int offset = helper.getOffset();
        List<ContactInfoDto> contacts = service.getAllWithOffset(offset);
        request.setAttribute("contactList", contacts);
    }

    private void deleteSelectedContacts() {
        logger.info("Deleting selected contacts");

        List<Long> ids = helper.getSelectedIds();
        service.deleteSelected(ids);
    }

    private void emailSelectedContacts() throws IOException {
        logger.info("Redirecting to email page with selected contacts");

        List<Long> ids = helper.getSelectedIds();
        List<String> emails = service.getEmailsOfSelected(ids);

        helper.processRedirectionToEmailPage(emails);
    }

}
