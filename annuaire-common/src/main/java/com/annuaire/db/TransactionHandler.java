package com.annuaire.db;


import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler {

    public static void run(Transaction transaction){
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);

            transaction.execute(connection);

            connection.commit();

        } catch (SQLException e) {
            //todo msg
            try {
                if (connection != null) {
                    connection.rollback();
                }

            } catch (SQLException e1) {
                //todo msg
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                //todo msg
            }
        }
    }
}
