package command;

import command.helpers.EmailHelper;
import properties.EmailPropertyService;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.EmailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmailCommand extends AbstractCommand{
    private final static Logger logger = LogManager.getLogger(EmailCommand.class);
    private EmailHelper helper;
    private EmailService service;

    public EmailCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        helper = new EmailHelper(request);
        service = new EmailService();
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = helper.getMethod();

        if (method.equals("POST")){
            sendEmail();
            redirect("/");
        }
    }

    private void sendEmail(){
        logger.info("Emails sent");

        Map<String, String> messageParams = helper.getMessageParams();
        service.sendEmail(messageParams);
    }
}
