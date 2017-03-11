package db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
    private static Logger logger = LogManager.getLogger(PropertyService.class);

    private static final String CONFIG_FILE_PATH = "db.properties";
    private final Properties properties;

    private static PropertyService instance;


    private PropertyService() {
        properties = new Properties();
        try (InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(stream);
        } catch (IOException e) {
            logger.error(e);
        }
    }


    public static PropertyService getInstance(){
        if (instance == null) {
            instance = new PropertyService();
        }
        return instance;
    }


    public String getJDBCDriverClassName() {
        return properties.getProperty("jdbc.driver");
    }


    public String getConnectionURL() {
        return properties.getProperty("connection.url");
    }
}
