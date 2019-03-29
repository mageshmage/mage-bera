package com.cargotracker.service;

import com.cargotracker.domain.State;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing State.
 */
public interface StateService {

    /**
     * Save a state.
     *
     * @param state the entity to save
     * @return the persisted entity
     */
    State save(State state);

    /**
     * Get all the states.
     *
     * @return the list of entities
     */
    List<State> findAll();


    /**
     * Get the "id" state.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<State> findOne(Long id);

    /**
     * Delete the "id" state.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the state corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<State> search(String query);
}
