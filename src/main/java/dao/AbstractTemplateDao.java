package dao;


import db.Connector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractTemplateDao<E, K> implements TemplateDao<E, K> {
    protected Connection connection;

    public AbstractTemplateDao() {
        connection = Connector.getConnection();
    }

    public abstract void save(E entity);
    public abstract List<E> getAll();
    public abstract E getById(K id);
    public abstract void update(E entity);
    public abstract void delete(K id);

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }

}
