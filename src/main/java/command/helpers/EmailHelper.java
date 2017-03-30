package command.helpers;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import properties.EmailPropertyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailHelper extends AbstractHelper{
    public EmailHelper(HttpServletRequest request) {
        super(request);
    }

    public Map<String, String> getMessageParams(){
        Map<String, String> params = new HashMap<>();
        params.put("subject", request.getParameter("subject"));
        params.put("message", request.getParameter("message"));
        params.put("emails", request.getParameter("emails"));

        return params;
    }
}
