package org.bank.processing_center.controller;

import java.util.List;

/**
 * Generic Controller interface
 * @param <T> Entity type
 * @param <ID> ID type
 */
public interface Controller<T, ID> {

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
     * Adds an entity
     * @param entity Entity to add
     */
    T addEntity(T entity);

    /**
     * Deletes an entity by ID
     * @param id Entity ID
     */
    void deleteEntity(ID id);

    /**
     * Retrieves all entities
     * @return List of all entities
     */
    List<T> getAllEntities();

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
    T updateEntity(T entity);
}