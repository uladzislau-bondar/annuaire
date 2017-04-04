package command;

import javax.servlet.ServletException;
import java.io.IOException;


public interface Command {
    void execute() throws ServletException, IOException;
    void process() throws ServletException, IOException;
}
