package command;


import dao.PhoneDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PhoneCommand extends AbstractCommand {
    private final static Logger logger = LogManager.getLogger(PhoneCommand.class);

    public PhoneCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException {
        String method = request.getMethod();
        Map<String, String> query = StringUtils.splitQuery(request.getQueryString());

        switch (method) {
            case "GET":
                if (query.isEmpty()) {
//                    showCreationForm();

                    forward("phone");
                } else if (query.containsKey("method")) {
                    if (query.get("method").equals("delete")) {
                        Long id = Long.valueOf(query.get("id"));
                        deletePhone(id);
                    }

                    Long contactId = Long.valueOf(query.get("contactId"));
                    redirect("/contact?id=" + contactId);
                }
//                } else if (query.containsKey("id")) {
//                    Long contactId = Long.valueOf(query.get("id"));
//                    showContact(contactId);
//
//                    forward("contact");
//                }

                break;
//            case "POST":
//                if (query.isEmpty()) {
//                    saveContact();
//                } else if (query.containsKey("id")) {
//                    Long contactId = Long.valueOf(query.get("id"));
//                    updateContact(contactId);
//                }
//
//                redirect("/");
//                break;
//            default:
//                forward("error");
        }
    }

    private void deletePhone(Long id) {
        PhoneDao phoneDao = new PhoneDao();
        phoneDao.delete(id);

        logger.info("Deleting phone #" + id);
    }
}
