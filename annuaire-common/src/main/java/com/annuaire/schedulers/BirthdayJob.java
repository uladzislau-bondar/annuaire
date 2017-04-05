package com.annuaire.schedulers;


import com.annuaire.dao.ContactDao;
import com.annuaire.db.TransactionHandler;
import com.annuaire.dto.ContactInitialsDto;
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

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirthdayJob implements Job {
    private final static Logger logger = LogManager.getLogger(BirthdayJob.class);
    private final EmailService service = new EmailService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        List<ContactInitialsDto> birthdayBoys = getBirthdayBoysList();
        if (birthdayBoys != null && !birthdayBoys.isEmpty()){
            try{
                Map<String, String> emailParams = buildEmailParams(birthdayBoys);
                service.sendEmail(emailParams);

                logger.info("Sending notification to admin, that someone have birthday today");
            } catch (ServiceException | IOException e){
                throw new JobExecutionException(e);
            }

        }
    }

    private Date today() {
        java.util.Date utilDate = new java.util.Date();
        return new Date(utilDate.getTime());
    }

    private List<ContactInitialsDto> getBirthdayBoysList() throws JobExecutionException{
        final List<ContactInitialsDto> contacts = new ArrayList<>();

        try{
            TransactionHandler.run(connection -> {
                ContactDao dao = new ContactDao(connection);
                contacts.addAll(dao.getInitialsByDateOfBirth(today()));
            });
        } catch (TransactionException e){
            throw new JobExecutionException(e);
        }

        return contacts;
    }

    private Map<String, String> buildEmailParams(List<ContactInitialsDto> birthdayBoys) throws IOException{
        EmailPropertyService properties = EmailPropertyService.getInstance();

        Map<String, String> params = new HashMap<>();
        params.put("subject", properties.getNotificationSubject());
        String message = Utils.buildBirthdayBoysListMessage(birthdayBoys) + " have birthday today";
        params.put("message", message);
        params.put("emails", properties.getAdminEmail());

        return params;
    }
}
