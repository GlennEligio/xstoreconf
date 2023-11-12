package com.glenneligio.xstoreconf.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);
    List<T> getAll();
    boolean save(T entity);
    boolean saveAll(List<T> entities);
    boolean deleteById(long id);
    T update(long id, T newEntity);
}
