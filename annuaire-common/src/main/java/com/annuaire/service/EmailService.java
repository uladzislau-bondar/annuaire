package com.annuaire.service;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.ContactInitialsDto;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import com.annuaire.properties.EmailPropertyService;

import java.io.IOException;
import java.util.*;

public class EmailService {
    public void sendEmail(Map<String, String> params) throws ServiceException, IOException{
        List<String> emails = buildEmailList(params.get("emails"));
        List<ContactInitialsDto> contacts = getContactListByEmails(emails);
        try {
            for (ContactInitialsDto contact : contacts) {
                Email email = configureEmail();
                email = fillWithParams(email, contact, params);
                email.send();
            }
        } catch (EmailException e) {
            throw new ServiceException(e);
        }

    }

    private Email configureEmail() throws EmailException, IOException {
        Email email = new SimpleEmail();

        EmailPropertyService properties = EmailPropertyService.getInstance();
        email.setHostName(properties.getHostname());
        email.setSmtpPort(properties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(properties.getUsername(), properties.getPassword()));
        email.setSSLOnConnect(true);

        email.setFrom(properties.getSender());

        return email;
    }

    private Email fillWithParams(Email email, ContactInitialsDto contact, Map<String, String> params) throws EmailException {
        email.setSubject(params.get("subject"));
        email.addTo(contact.getEmail());

        String templatesLocation = params.get("templatesLocation");
        String templateName = params.get("template");
        StringTemplate template = TemplateService.parseTemplate(templatesLocation, templateName);
        if (template != null){
            template.setAttribute("firstName", contact.getFirstName());
            template.setAttribute("lastName", contact.getLastName());

            email.setMsg(buildMessage(templateName, template, params.get("message")));
        }

        return email;
    }

    private List<String> buildEmailList(String listInString) {
        return Arrays.asList(listInString.split("\\s*;\\s*"));
    }

    private List<ContactInitialsDto> getContactListByEmails(List<String> emails) throws ServiceException{
        final List<ContactInitialsDto> contacts = new ArrayList<>();
        try {
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                for (String e : emails) {
                    contacts.add(dao.getInitialsByEmail(e));
                }
            });
        } catch (TransactionException e) {
            throw new ServiceException(e);
        }

        return contacts;
    }

    private String buildMessage(String templateName, StringTemplate template, String defaultMessage){
        StringBuilder builder = new StringBuilder();
        builder.append(template.toString());

        if (templateName.equals("default")){
            builder.append("\n");
            builder.append(defaultMessage);
        }

        return builder.toString();
    }
}
