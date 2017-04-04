package command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandFactory {
    public static Command create(HttpServletRequest request, HttpServletResponse response){
        String path = request.getPathInfo();

        Command command = null;
        switch (path) {
            case "/":
            case "/contacts":
                command = new ContactListCommand(request, response);
                break;
            case "/contact":
                command = new ContactCommand(request, response);
                break;
            case "/attachment":
                command = new AttachmentCommand(request, response);
                break;
            case "/email":
                command = new EmailCommand(request, response);
                break;
            case "/search":
                command = new SearchCommand(request, response);
                break;
            case "/photo":
                command = new PhotoCommand(request, response);
                break;
            default:
                // todo process error
        }

        return command;
    }
}
