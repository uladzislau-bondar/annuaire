package com.annuaire.dao;


import java.sql.SQLException;
import java.util.List;

public interface TemplateDao<E, K> {
    K save(E entity) throws SQLException;

    List<E> getAll() throws SQLException;

    E getById(K id) throws SQLException;

    void update(E entity) throws SQLException;

    void delete(K id) throws SQLException;
}
