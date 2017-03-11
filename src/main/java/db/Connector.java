package db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Logger logger = LogManager.getLogger(Connector.class);

    private static Connection connection;

    private Connector() {
    }


    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String jdbcDriverClassName = PropertyService.getInstance().getJDBCDriverClassName();
                Driver driver = (Driver) Class.forName(jdbcDriverClassName).newInstance();
                DriverManager.registerDriver(driver);

                String url = PropertyService.getInstance().getConnectionURL();
                connection = DriverManager.getConnection(url);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            logger.error(e);
        }
        return connection;

    }
}