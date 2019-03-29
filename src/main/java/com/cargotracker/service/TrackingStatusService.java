package com.cargotracker.service;

import com.cargotracker.domain.TrackingStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TrackingStatus.
 */
public interface TrackingStatusService {

    /**
     * Save a trackingStatus.
     *
     * @param trackingStatus the entity to save
     * @return the persisted entity
     */
    TrackingStatus save(TrackingStatus trackingStatus);

    /**
     * Get all the trackingStatuses.
     *
     * @return the list of entities
     */
    List<TrackingStatus> findAll();


    /**
     * Get the "id" trackingStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TrackingStatus> findOne(Long id);

    /**
     * Delete the "id" trackingStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the trackingStatus corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TrackingStatus> search(String query);
}
