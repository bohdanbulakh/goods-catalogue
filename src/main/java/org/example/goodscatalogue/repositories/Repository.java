package org.example.goodscatalogue.repositories;

import java.util.List;

public interface Repository <T, Id> {
    List<T> findAll();
    T findById(Id id);
    T create(T entity);
    T updateById(Id id, T entity);
    void deleteById(Id id);
}