package dao;


import java.util.List;

public interface EntityDao<E, K> {
    void create(E entity);

    List<E> getAll();
    E getById(K id);

    E update(E entity);

    void delete(K id);
}
