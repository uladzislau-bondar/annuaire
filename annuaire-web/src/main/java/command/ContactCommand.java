package command;

import com.annuaire.exceptions.ServiceException;
import command.helpers.ContactHelper;
import com.annuaire.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.annuaire.service.ContactService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


// todo if nothing found
public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);
    private ContactService service;
    private ContactHelper helper;

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        service = new ContactService();
        helper = new ContactHelper(request, response);
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
        Map<String, String> params = helper.getQuery();

        if (params.isEmpty()) {
            showCreationForm();
            forward("contact");
        } else if (params.containsKey("id")) {
            String idParam = params.get("id");
            if (StringUtils.isNotEmpty(idParam)){
                Long contactId = Long.valueOf(idParam);
                if (params.containsKey("method")) {
                    String queryMethod = params.get("method");
                    processMethodForContact(contactId, queryMethod);
                } else{
                    showContact(contactId);
                }
            }
        } else {
            throw new ServletException("Invalid params.");
        }
    }

    private void processPost() throws ServletException, IOException{
        Map<String, String> params = helper.getQuery();

        if (params.isEmpty()) {
            saveContact();
        } else if (params.containsKey("id")) {
            String idParam = params.get("id");
            if (StringUtils.isNotEmpty(idParam)){
                Long contactId = Long.valueOf(idParam);
                updateContact(contactId);
            }
        } else{
            throw new ServletException("Invalid POST params.");
        }

        redirect("/");
    }

    private void processMethodForContact(Long id, String method) throws ServletException, IOException{
        switch (method) {
            case "delete":
                deleteContact(id);
                redirect("/");
                break;
            case "show":
                showContact(id);
                forward("contact");
                break;
            default:
                throw new ServletException("No such method declared.");
        }
    }

    private void showCreationForm() {
        logger.info("Show form for creating new contact");
        setTitle("Contact Creation Form");
    }

    private void showContact(Long id) throws ServletException {
        logger.info("Show form for editing contact #{}", id);
        setTitle("Contact " + id);

        try {
            ContactDatabaseDto contact = service.get(id);
            helper.showContact(contact);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private void saveContact() throws ServletException, IOException {
        logger.info("Saving new contact");

        try {
            ContactFrontDto contact = helper.getContact();
            service.save(contact);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private void updateContact(Long id) throws ServletException, IOException {
        logger.info("Updating contact #{}", id);

        try {
            ContactFrontDto contact = helper.getContact();
            service.update(contact, id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private void deleteContact(Long id) throws ServletException {
        logger.info("Deleting contact #{}", id);

        try {
            service.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
