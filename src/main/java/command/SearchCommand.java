package command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SearchCommand extends AbstractCommand {
    //todo search command

    public SearchCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void execute() throws ServletException, IOException {
        process();
    }

    @Override
    public void process() throws ServletException, IOException{
        String method = request.getMethod();

        switch (method){
            case "GET":
                showSearchForm();

                forward("search");
                break;
            case "POST":
                search();

                //todo redirect to index page
                break;
            default:
                forward("error");
                break;
        }
    }

    private void showSearchForm() {
    }

    private void search() {
    }
}
