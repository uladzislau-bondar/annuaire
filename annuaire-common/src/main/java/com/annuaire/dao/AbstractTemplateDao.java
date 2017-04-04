package com.annuaire.dao;


import java.sql.*;
import java.util.List;

public abstract class AbstractTemplateDao<E, K> implements TemplateDao<E, K> {
    protected Connection connection;

    public AbstractTemplateDao(Connection connection) {
        this.connection = connection;
    }

    public abstract K save(E entity);
    public abstract List<E> getAll();
    public abstract E getById(K id);
    public abstract void update(E entity);
    public abstract void delete(K id);

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }

    protected PreparedStatement getPreparedStatementAndReturnGeneratedKeys(String sql) throws SQLException{
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    protected Long obtainIdFromStatement(Statement statement) throws SQLException{
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating entity failed, no ID obtained.");
            }
        }
    }
}
