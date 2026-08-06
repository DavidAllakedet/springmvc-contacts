package com.contact.app.dao;

import java.util.List;

public interface Repository<T> {
    T save(T entity);
    T update(T entity);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
