package command;

import javax.servlet.ServletException;
import java.io.IOException;


public interface Command {
    void process() throws ServletException, IOException;
    void forward(String jspName) throws ServletException, IOException;
}
