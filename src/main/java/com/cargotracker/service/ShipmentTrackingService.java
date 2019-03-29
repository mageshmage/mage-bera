package com.cargotracker.service;

import com.cargotracker.domain.ShipmentTracking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShipmentTracking.
 */
public interface ShipmentTrackingService {

    /**
     * Save a shipmentTracking.
     *
     * @param shipmentTracking the entity to save
     * @return the persisted entity
     */
    ShipmentTracking save(ShipmentTracking shipmentTracking);

    /**
     * Get all the shipmentTrackings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentTracking> findAll(Pageable pageable);


    /**
     * Get the "id" shipmentTracking.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentTracking> findOne(Long id);

    /**
     * Delete the "id" shipmentTracking.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentTracking corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentTracking> search(String query, Pageable pageable);
}
