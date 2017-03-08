package command;

import javax.servlet.ServletException;
import java.io.IOException;


public interface Command {
    void process();
    void forward(String jspName) throws ServletException, IOException;
}
