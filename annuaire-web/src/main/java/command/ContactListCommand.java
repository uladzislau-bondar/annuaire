package command;


import com.annuaire.exceptions.ServiceException;
import command.helpers.ContactListHelper;
import com.annuaire.dto.ContactInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.annuaire.service.ContactListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ContactListCommand extends AbstractCommand {
    private static Logger logger = LogManager.getLogger(ContactListCommand.class);
    private ContactListService contactListService;
    private ContactListHelper helper;

    public ContactListCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        contactListService = new ContactListService();
        helper = new ContactListHelper(request, response);
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
                processGet();
                break;
            case "POST":
                processPost();
                break;
            default:
                throw new ServletException("Can't process" + method);
        }
    }

    private void processGet() throws ServletException, IOException {
        String methodParam = helper.getMethodParam();

        if (methodParam == null || methodParam.equals("show")) {
            showContactList();
            forwardWithMethod("index", "show");
        } else {
            throw new ServletException("No such method declared.");
        }
    }

    private void processPost() throws ServletException, IOException {
        String methodParam = helper.getMethodParam();

        switch (methodParam) {
            case "delete":
                deleteSelectedContacts();
                break;
            case "email":
                emailSelectedContacts();
                break;
            default:
                throw new ServletException("Invalid POST params.");
        }
    }

    private void showContactList() throws ServletException {
        logger.info("Showing contacts");

        try {
            int offset = helper.getOffset();
            List<ContactInfoDto> contacts = contactListService.getAllWithOffset(offset);
            request.setAttribute("contactList", contacts);

            setTitle("List of contacts");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private void deleteSelectedContacts() throws ServletException, IOException{
        logger.info("Deleting selected contacts");

        try {
            List<Long> ids = helper.getSelectedIds();
            if (!ids.isEmpty()){
                contactListService.deleteSelected(ids);
            }
            redirect("/");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }

    private void emailSelectedContacts() throws ServletException, IOException {
        logger.info("Redirecting to email page with selected contacts");

        try {
            List<Long> ids = helper.getSelectedIds();
            if (!ids.isEmpty()){
                List<String> emails = contactListService.getEmailsOfSelected(ids);
                helper.redirectToEmailPageWithList(emails);
            } else{
                helper.redirectToEmailPage();
            }

            forward("email");
            setTitle("Email page");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

}
