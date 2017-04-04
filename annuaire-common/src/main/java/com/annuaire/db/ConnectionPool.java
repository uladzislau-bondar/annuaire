package com.annuaire.db;


import org.apache.commons.dbcp2.BasicDataSource;
import com.annuaire.properties.DBPropertyService;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static BasicDataSource instance;

    public static javax.sql.DataSource getDataSource(){
        if (instance == null){
            instance = new BasicDataSource();

            DBPropertyService properties = DBPropertyService.getInstance();
            instance.setUrl(properties.getJDBCUrl());
            instance.setDriverClassName(properties.getJDBCDriver());
            instance.setUsername(properties.getJDBCUsername());
            instance.setPassword(properties.getJDBCPassword());

            instance.setMaxIdle(30);
            instance.setMaxWaitMillis(10000);
            instance.setMaxOpenPreparedStatements(50);
        }

        return instance;
    }

    public static Connection getConnection() throws SQLException{
        return getDataSource().getConnection();
    }
}
