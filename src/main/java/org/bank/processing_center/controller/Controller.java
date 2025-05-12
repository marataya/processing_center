package org.bank.processing_center.controller;

import java.util.List;
import java.util.Optional;

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
    void addEntity(T entity);

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
     * @return Optional containing the entity if found
     */
    Optional<T> findById(ID id);

    /**
     * Updates an existing entity
     * @param entity Entity to update
     */
    void updateEntity(T entity);
}