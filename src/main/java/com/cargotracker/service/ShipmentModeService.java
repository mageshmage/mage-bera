package com.cargotracker.service;

import com.cargotracker.domain.ShipmentMode;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentMode.
 */
public interface ShipmentModeService {

    /**
     * Save a shipmentMode.
     *
     * @param shipmentMode the entity to save
     * @return the persisted entity
     */
    ShipmentMode save(ShipmentMode shipmentMode);

    /**
     * Get all the shipmentModes.
     *
     * @return the list of entities
     */
    List<ShipmentMode> findAll();


    /**
     * Get the "id" shipmentMode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentMode> findOne(Long id);

    /**
     * Delete the "id" shipmentMode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentMode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ShipmentMode> search(String query);
}
