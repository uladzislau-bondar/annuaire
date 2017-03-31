package command;

import command.helpers.ContactHelper;
import dto.*;
import entities.Attachment;
import entities.Contact;
import entities.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactService;
import util.DtoUtils;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ContactCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(ContactCommand.class);
    private ContactService service;
    private ContactHelper helper;

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        service = new ContactService();
        helper = new ContactHelper(request);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();
        Map<String, String> query = helper.getQuery();

        // todo look awful
        switch (method) {
            case "GET":
                if (query.isEmpty()) {
                    showCreationForm();
                    forward("contact");
                } else if (query.containsKey("id")) {
                    Long contactId = Long.valueOf(query.get("id"));
                    if (query.containsKey("method")) {
                        String queryMethod = query.get("method");
                        if (queryMethod.equals("delete")){
                            deleteContact(contactId);
                            redirect("/");
                        } else if (queryMethod.equals("show")){
                            showContact(contactId);
                            forward("contact");
                        }
                    }
                }

                break;
            case "POST":
                if (query.isEmpty()) {
                    saveContact();
                } else if (query.containsKey("id")) {
                    Long contactId = Long.valueOf(query.get("id"));
                    updateContact(contactId);
                }

                redirect("/");
                break;
            default:
                forward("error");
                break;
        }


    }

    private void showCreationForm() {
        logger.info("Show form for creating new contact");
        setTitle("Contact Creation Form");
    }

    private void showContact(Long id) {
        logger.info("Show form for editing contact #{}", id);
        setTitle("Contact " + id);

        ContactDatabaseDto contact = service.get(id);
        helper.showContact(contact);
    }

    private void saveContact() throws ServletException, IOException{
        logger.info("Saving new contact");

        ContactFrontDto contact = helper.getContact();
        service.save(contact);
    }

    private void updateContact(Long id) throws ServletException, IOException{
        logger.info("Updating contact #{}", id);

        ContactFrontDto contact = helper.getContact();
        service.update(contact, id);
    }

    private void deleteContact(Long id) {
        logger.info("Deleting contact #{}", id);

        service.delete(id);
    }
}
