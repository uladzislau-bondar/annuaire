package schedulers;


import dao.ContactDao;
import db.ConnectionPool;
import properties.EmailPropertyService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BirthdayJob implements Job {
    private final static Logger logger = LogManager.getLogger(BirthdayJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<String> birthdayBoysEmails = getBirthdayBoysEmails();
        String birthdayList = String.join("; ", birthdayBoysEmails);
        sendEmail(birthdayList);

        logger.info("Sending notification to admin, that " + birthdayList + "have birthday today");
    }

    private Date today() {
        java.util.Date utilDate = new java.util.Date();
        return new Date(utilDate.getTime());
    }

    private List<String> getBirthdayBoysEmails() {
        List<String> emails = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
        } catch (SQLException e) {
            logger.error(e);
        }

        ContactDao dao = new ContactDao(connection);
        return dao.getEmailsByDateOfBirth(today());
    }

    private void sendEmail(String birthdayList) {
        EmailPropertyService properties = EmailPropertyService.getInstance();

        Email email = new SimpleEmail();
        email.setHostName(properties.getHostname());
        email.setSmtpPort(properties.getPort());
        email.setAuthenticator(new DefaultAuthenticator(properties.getUsername(), properties.getPassword()));
        email.setSSLOnConnect(true);

        try {
            email.setFrom(properties.getSender());
            email.setSubject("Today's birthday boys");
            String message = birthdayList + " have birthday today!";
            email.setMsg(message);
            email.addTo(properties.getAdminEmail());
            email.send();
        } catch (EmailException e) {
            logger.error(e);
        }
    }
}
