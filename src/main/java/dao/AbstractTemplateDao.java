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
    private Logger logger = LogManager.getLogger(AbstractTemplateDao.class);
    protected Connection connection;

    public AbstractTemplateDao() {
        connection = Connector.getConnection();
    }

    public abstract void save(E entity);
    public abstract List<E> getAll();
    public abstract E getById(K id);
    public abstract void update(E entity);
    public abstract void delete(K id);

    protected PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error(e);
        }

        return statement;
    }

    protected void closePreparedStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

    protected ResultSet getResultSet(PreparedStatement statement){
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            logger.error(e);
        }

        return resultSet;
    }

    protected void closeResultSet(ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }

}
