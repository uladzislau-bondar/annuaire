package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandFactory {
    public static Command create(HttpServletRequest request, HttpServletResponse response){
        String path = request.getPathInfo();
        String queryString = request.getQueryString();

        Command command = null;
        if (path.equals("/")){
            command = new ContactListCommand(request, response);
        }
        else if (path.equals("/contacts") && queryString.contains("id=123")){
            command = new ContactCommand(request, response);
        }

        return command;
    }
}
