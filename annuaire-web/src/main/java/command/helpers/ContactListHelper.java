package command.helpers;


import com.annuaire.service.TemplateService;
import com.annuaire.util.Utils;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ContactListHelper extends AbstractHelper{
    public ContactListHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public List<Long> getSelectedIds(){
        return Utils.toLongList(request.getParameterValues("selected"));
    }

    public void redirectToEmailPageWithList(List<String> emails){
        String emailsList = String.join(";", emails);
        request.setAttribute("emails", emailsList);
        redirectToEmailPage();
    }
}
