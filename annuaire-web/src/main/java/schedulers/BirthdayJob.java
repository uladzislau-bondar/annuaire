package schedulers;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.exceptions.ServiceException;
import com.annuaire.exceptions.TransactionException;
import com.annuaire.properties.EmailPropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.annuaire.service.EmailService;
import com.annuaire.util.Utils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo send name instead of email
public class BirthdayJob implements Job {
    private final static Logger logger = LogManager.getLogger(BirthdayJob.class);
    private final EmailService service = new EmailService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        List<String> birthdayBoysEmails = getBirthdayBoysEmails();
        if (birthdayBoysEmails != null && !birthdayBoysEmails.isEmpty()){
            String birthdayList = Utils.joinListWithSemicolon(birthdayBoysEmails);
            try{
                Map<String, String> emailParams = buildEmailParams(birthdayList);
                service.sendEmail(emailParams);

                logger.info("Sending notification to admin, that " + birthdayList + "have birthday today");
            } catch (ServiceException | IOException e){
                throw new JobExecutionException(e);
            }

        }
    }

    private Date today() {
        java.util.Date utilDate = new java.util.Date();
        return new Date(utilDate.getTime());
    }

    private List<String> getBirthdayBoysEmails() throws JobExecutionException{
        final List<String> emails = new ArrayList<>();

        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                emails.addAll(dao.getEmailsByDateOfBirth(today()));
            });
        } catch (TransactionException e){
            throw new JobExecutionException(e);
        }

        return Utils.deleteNulls(emails);
    }

    private Map<String, String> buildEmailParams(String emailList) throws IOException{
        EmailPropertyService properties = EmailPropertyService.getInstance();

        Map<String, String> params = new HashMap<>();
        params.put("subject", properties.getNotificationSubject());
        String message = emailList + " have birthday today";
        params.put("message", message);
        params.put("emails", properties.getAdminEmail());

        return params;
    }
}
