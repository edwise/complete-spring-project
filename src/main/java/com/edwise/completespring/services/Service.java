package com.edwise.completespring.services;

import java.util.List;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface Service<T, I> {
    /**
     * Returns a List of entities.
     *
     * @return a page of entities
     */
    List<T> findAll();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(I id);


    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(I id);
}
