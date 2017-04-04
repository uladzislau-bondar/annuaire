package com.annuaire.db;


import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {
    void execute(Connection connection) throws SQLException;
}
