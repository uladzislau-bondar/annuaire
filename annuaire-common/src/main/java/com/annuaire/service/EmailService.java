package com.annuaire.service;


import com.annuaire.exceptions.ServiceException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import com.annuaire.properties.EmailPropertyService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmailService {
    public void sendEmail(Map<String, String> params) throws ServiceException, IOException{
        try {
            Email email = configureEmail();
            email = fillWithParams(email, params);
            email.send();
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

    private Email fillWithParams(Email email, Map<String, String> params) throws EmailException {
        email.setSubject(params.get("subject"));
        email.setMsg(params.get("message"));
        List<String> emailList = buildEmailList(params.get("emails"));
        for (String s : emailList) {
            email.addTo(s);
        }

        return email;
    }

    private List<String> buildEmailList(String listInString) {
        return Arrays.asList(listInString.split("\\s*;\\s*"));
    }
}
