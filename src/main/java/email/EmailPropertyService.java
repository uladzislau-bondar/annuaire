package email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailPropertyService {
    private static Logger logger = LogManager.getLogger(EmailPropertyService.class);

    private static final String CONFIG_FILE_PATH = "email.properties";
    private final Properties properties;

    private static EmailPropertyService instance;


    private EmailPropertyService() {
        properties = new Properties();
        try (InputStream stream = EmailPropertyService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            logger.error(e);
        }
    }


    public static EmailPropertyService getInstance(){
        if (instance == null) {
            instance = new EmailPropertyService();
        }
        return instance;
    }


    public String getHostname() {
        return properties.getProperty("hostname");
    }


    public int getPort() {
        return Integer.valueOf(properties.getProperty("port"));
    }

    public String getUsername(){ return properties.getProperty("username");}

    public String getPassword(){return properties.getProperty("password");}
}

