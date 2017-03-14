package dao;


import java.util.List;

public interface TemplateDao<E, K> {
    void create(E entity);

    List<E> getAll();

    E getById(K id);

    E update(E entity);

    void delete(K id);
}
