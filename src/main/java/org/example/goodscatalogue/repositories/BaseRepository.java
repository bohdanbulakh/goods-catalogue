package org.example.goodscatalogue.repositories;

import org.example.goodscatalogue.models.Base;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class BaseRepository<T extends Base> {
    protected final Map<Integer, T> storage = new HashMap<>();
    protected final AtomicInteger intIdGenerator = new AtomicInteger(1);

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public T findById(Integer id) {
        return storage.get(id);
    }

    public T create(T entity) {
        Integer id = entity.getId();
        if (id == null) {
            id = generateId();
            entity.setId(id);
        }
        storage.put(id, entity);
        return entity;
    }

    public T updateById(Integer id, T entity) {
        if (!storage.containsKey(id)) return null;
        entity.setId(id);
        storage.put(id, entity);
        return entity;
    }

    public void deleteById(Integer id) {
        storage.remove(id);
    }

    protected Integer generateId() {
        return intIdGenerator.getAndIncrement();
    }
}
