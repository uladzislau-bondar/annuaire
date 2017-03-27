package properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import properties.PropertyService;

import java.io.IOException;
import java.io.InputStream;

public class EmailPropertyService {
    private static PropertyService service;
    private static EmailPropertyService instance;


    private EmailPropertyService() {
        service = new PropertyService("email");
    }


    public static EmailPropertyService getInstance(){
        if (instance == null) {
            instance = new EmailPropertyService();
        }
        return instance;
    }


    public String getHostname() {
        return service.getProperty("hostname");
    }


    public int getPort() {
        return Integer.valueOf(service.getProperty("port"));
    }

    public String getUsername(){ return service.getProperty("username");}

    public String getPassword(){return service.getProperty("password");}

    public String getAdminEmail(){return service.getProperty("admin.email");}

    public String getSender(){return service.getProperty("from");}
}

