package com.cargotracker.service;

import com.cargotracker.service.dto.ShipmentModeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentMode.
 */
public interface ShipmentModeService {

    /**
     * Save a shipmentMode.
     *
     * @param shipmentModeDTO the entity to save
     * @return the persisted entity
     */
    ShipmentModeDTO save(ShipmentModeDTO shipmentModeDTO);

    /**
     * Get all the shipmentModes.
     *
     * @return the list of entities
     */
    List<ShipmentModeDTO> findAll();
    
    List<ShipmentModeDTO> findAllByVendor(Long vendorId);


    /**
     * Get the "id" shipmentMode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentModeDTO> findOne(Long id);

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
    //List<ShipmentModeDTO> search(String query);
}
