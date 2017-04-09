package command;

import com.annuaire.exceptions.ServiceException;
import com.annuaire.service.TemplateService;
import command.helpers.EmailHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.annuaire.service.EmailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class EmailCommand extends AbstractCommand{
    private final static Logger logger = LogManager.getLogger(EmailCommand.class);
    private final static String ALERT_EMAILS_SENT = "Письма успешно отправлены!";
    private EmailHelper helper;
    private EmailService service;

    public EmailCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        helper = new EmailHelper(request, response);
        service = new EmailService();
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
                showEmailForm();
                break;
            case "POST":
                sendEmail();
                redirect("/");
                break;
            default:
                throw new ServletException("Can't process " + method);
        }
    }

    private void showEmailForm() throws ServletException, IOException{
        logger.info("Showing email form");

        helper.redirectToEmailPage();
        setTitle("Отправка email");
        forward("email");
    }

    private void sendEmail() throws ServletException, IOException{
        logger.info("Emails are sending");

        try{
            Map<String, String> messageParams = helper.getMessageParams();
            service.sendEmail(messageParams);
            helper.setAlertMessage(ALERT_EMAILS_SENT);
        } catch (ServiceException e){
            throw new ServletException(e);
        }
    }
}
