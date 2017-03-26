package command;


import email.EmailPropertyService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EmailCommand extends AbstractCommand{
    private final static Logger logger = LogManager.getLogger(EmailCommand.class);

    public EmailCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")){
            sendEmail();
            redirect("/");
        }
    }

    private void sendEmail() {
        EmailPropertyService properties = EmailPropertyService.getInstance();

        Email email = new SimpleEmail();
        email.setHostName(properties.getHostname());
        email.setSmtpPort(properties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(properties.getUsername(), properties.getPassword()));
        email.setSSLOnConnect(true);

        try{
            email.setFrom("colinforzeal@yandex.ru");
            String subject = request.getParameter("subject");
            logger.info(subject);
            email.setSubject(subject);
            String message = request.getParameter("message");
            logger.info(message);
            email.setMsg(message);
            String emails = request.getParameter("emails");
            logger.info(emails);
            List<String> emailList = Arrays.asList(emails.split("\\s*;\\s*"));
            for (String s : emailList) {
                email.addTo(s);
            }
            email.send();
        } catch (EmailException e){
            logger.error(e);
        }

    }
}
