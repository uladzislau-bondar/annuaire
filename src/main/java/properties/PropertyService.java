package properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
    private static Logger logger = LogManager.getLogger(PropertyService.class);

    private final Properties properties;
    private static final String EXTENSION = ".properties";

    public PropertyService(String name){
        properties = new Properties();
        String propFileName = name + EXTENSION;
        try (InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(propFileName)) {
            properties.load(stream);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public String getProperty(String name){
        return properties.getProperty(name);
    }
}
