package com.edwise.completespring.services;

import java.util.List;

public interface Service<T, I> {

    List<T> findAll();
    T save(T entity);
    T findOne(I id);
    void delete(I id);
}
