package command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SearchCommand extends Command{

    public SearchCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() {

    }

    @Override
    protected void forward(String jspName) throws ServletException, IOException {
        super.forward(jspName);
    }
}
