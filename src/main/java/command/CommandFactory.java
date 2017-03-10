package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandFactory {
    public static Command create(HttpServletRequest request, HttpServletResponse response){
        String path = request.getPathInfo();

        Command command = null;
        if (path.equals("/") || path.equals("/contacts")){
            command = new ContactListCommand(request, response);
        }
        else if (path.equals("/contact")){
            command = new ContactCommand(request, response);
        }

        return command;
    }
}
