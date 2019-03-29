package com.cargotracker.service;

import com.cargotracker.domain.ShipmentType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentType.
 */
public interface ShipmentTypeService {

    /**
     * Save a shipmentType.
     *
     * @param shipmentType the entity to save
     * @return the persisted entity
     */
    ShipmentType save(ShipmentType shipmentType);

    /**
     * Get all the shipmentTypes.
     *
     * @return the list of entities
     */
    List<ShipmentType> findAll();


    /**
     * Get the "id" shipmentType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentType> findOne(Long id);

    /**
     * Delete the "id" shipmentType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ShipmentType> search(String query);
}
