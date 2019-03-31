package com.cargotracker.service;

import com.cargotracker.service.dto.ShipmentInfoPODDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentInfoPOD.
 */
public interface ShipmentInfoPODService {

    /**
     * Save a shipmentInfoPOD.
     *
     * @param shipmentInfoPODDTO the entity to save
     * @return the persisted entity
     */
    ShipmentInfoPODDTO save(ShipmentInfoPODDTO shipmentInfoPODDTO);

    /**
     * Get all the shipmentInfoPODS.
     *
     * @return the list of entities
     */
    List<ShipmentInfoPODDTO> findAll();


    /**
     * Get the "id" shipmentInfoPOD.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentInfoPODDTO> findOne(Long id);

    /**
     * Delete the "id" shipmentInfoPOD.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentInfoPOD corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ShipmentInfoPODDTO> search(String query);
}
