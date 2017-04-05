package com.annuaire.properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyService {
    private final Properties properties;
    private static final String EXTENSION = ".properties";

    public PropertyService(String name) throws IOException {
        properties = new Properties();
        String propFileName = name + EXTENSION;

        InputStream stream = PropertyService.class.getClassLoader().getResourceAsStream(propFileName);
        properties.load(stream);
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
