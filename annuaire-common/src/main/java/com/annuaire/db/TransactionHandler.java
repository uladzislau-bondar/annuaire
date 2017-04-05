package com.annuaire.db;


import com.annuaire.exceptions.TransactionException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler {

    public static void run(Transaction transaction) throws TransactionException{
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            transaction.execute(connection);

            connection.commit();

        } catch (SQLException | IOException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException e1) {
                throw new TransactionException(e1);
            }
            throw new TransactionException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                throw new TransactionException(e);
            }
        }
    }
}
