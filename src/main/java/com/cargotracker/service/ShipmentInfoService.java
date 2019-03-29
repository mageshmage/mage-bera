package com.cargotracker.service;

import com.cargotracker.domain.ShipmentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShipmentInfo.
 */
public interface ShipmentInfoService {

    /**
     * Save a shipmentInfo.
     *
     * @param shipmentInfo the entity to save
     * @return the persisted entity
     */
    ShipmentInfo save(ShipmentInfo shipmentInfo);

    /**
     * Get all the shipmentInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentInfo> findAll(Pageable pageable);


    /**
     * Get the "id" shipmentInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentInfo> findOne(Long id);

    /**
     * Delete the "id" shipmentInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentInfo> search(String query, Pageable pageable);
}
