package org.bank.processing_center.service;

import java.util.List;

/**
 * Generic Service interface
 * @param <T> Entity type
 * @param <ID> ID type
 */
public interface Service<T, ID> {

    /**
     * Creates a table for the entity if it doesn't exist
     */
    void createTable();

    /**
     * Drops the table for the entity if it exists
     */
    void dropTable();

    /**
     * Clears all data from the table
     */
    void clearTable();

    /**
     * Saves an entity
     * @param entity Entity to save
     */
    // return value
    void save(T entity);

    /**
     * Deletes an entity by ID
     * @param id Entity ID
     */
    void delete(ID id);

    /**
     * Retrieves all entities
     * @return List of all entities
     */
    List<T> findAll();

    /**
     * Finds an entity by ID
     * @param id Entity ID
     * @return entity if found
     */
    T findById(ID id);

    /**
     * Updates an existing entity
     * @param entity Entity to update
     */
    // return value
    void update(T entity);
}
