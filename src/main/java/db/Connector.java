package db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import properties.DBPropertyService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Logger logger = LogManager.getLogger(Connector.class);

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String jdbcDriverClassName = DBPropertyService.getInstance().getJDBCDriver();
                Driver driver = (Driver) Class.forName(jdbcDriverClassName).newInstance();
                DriverManager.registerDriver(driver);

                String url = DBPropertyService.getInstance().getJDBCUrl();
                String username = DBPropertyService.getInstance().getJDBCUsername();
                String password = DBPropertyService.getInstance().getJDBCPassword();

                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            logger.error(e);
        }

        return connection;
    }
}