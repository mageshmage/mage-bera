package com.cargotracker.service;

import com.cargotracker.service.dto.ShipmentTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentType.
 */
public interface ShipmentTypeService {

    /**
     * Save a shipmentType.
     *
     * @param shipmentTypeDTO the entity to save
     * @return the persisted entity
     */
    ShipmentTypeDTO save(ShipmentTypeDTO shipmentTypeDTO);

    /**
     * Get all the shipmentTypes.
     *
     * @return the list of entities
     */
    List<ShipmentTypeDTO> findAll();


    /**
     * Get the "id" shipmentType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentTypeDTO> findOne(Long id);

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
    List<ShipmentTypeDTO> search(String query);
}
