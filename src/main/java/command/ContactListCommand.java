package command;


import command.helpers.ContactListHelper;
import dto.ContactInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactListService;
import service.SearchService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactListCommand.class);
    private ContactListService contactListService;
    private SearchService searchService;
    private ContactListHelper helper;

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        contactListService = new ContactListService();
        searchService = new SearchService();
        helper = new ContactListHelper(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();
        String methodParam = helper.getMethodParam();

        switch (method) {
            case "GET":
                if (methodParam == null || methodParam.equals("show")){
                    showContactList();
                    forwardWithMethod("index", "show");
                }
                break;
            case "POST":
                if (methodParam != null) {
                    if (methodParam.equals("delete")) {
                        deleteSelectedContacts();
                        redirect("/");
                    } else if (methodParam.equals("email")) {
                        emailSelectedContacts();
                        forward("email");
                    }
                }
                break;
            default:
                forward("error");
                break;
        }
    }

    private void showContactList() {
        logger.info("Showing contacts");

        int offset = helper.getOffset();
        List<ContactInfoDto> contacts = contactListService.getAllWithOffset(offset);
        request.setAttribute("contactList", contacts);

        setTitle("List of contacts");
    }

    private void deleteSelectedContacts() {
        logger.info("Deleting selected contacts");

        List<Long> ids = helper.getSelectedIds();
        contactListService.deleteSelected(ids);
    }

    private void emailSelectedContacts() throws IOException {
        logger.info("Redirecting to email page with selected contacts");

        List<Long> ids = helper.getSelectedIds();
        List<String> emails = contactListService.getEmailsOfSelected(ids);

        helper.processRedirectionToEmailPage(emails);
        setTitle("Email page");
    }

}
