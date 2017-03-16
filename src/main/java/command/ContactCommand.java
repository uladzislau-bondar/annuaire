package command;

import dao.ContactDao;
import entities.Contact;
import entities.ContactBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;


public class ContactCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger(ContactCommand.class);

    public ContactCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
        forward("contact");
    }

    @Override
    public void process() throws ServletException, IOException {
        String queryString = request.getQueryString();

        if (queryString != null) {

        } else {
            String method = request.getMethod();
            log.info(method);
            if (method.equals("GET")){
                showNewContactForm();
            }
            else if (method.equals("POST")){
                createNewContact();
            }
        }
    }

    @Override
    public void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }

    private void showNewContactForm() {
        String title = "Create new contact";
        request.setAttribute("title", title);
    }

    private void createNewContact(){
        //todo looks awful but works
        Object o = null;

        try{
            Class<?> type = Class.forName("entities.ContactBuilder");
            o = type.newInstance();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()){
                String name = paramNames.nextElement();
                String value = request.getParameter(name);
                try{
                    Method method = type.getMethod(name, value.getClass());
                    log.info("Method name: " + method.getName());
                    o = method.invoke(o, value);
                }
                catch (InvocationTargetException | NoSuchMethodException e){
                    log.error(e);
                }
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
            log.error(e);
        }

        ContactBuilder builder = (ContactBuilder) o;
        Contact contact = builder.build();

        ContactDao contactDao = new ContactDao();
        contactDao.save(contact);

        log.info(contact.getFirstName());
    }
}
