package com.cargotracker.service;

import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentTrackingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentTracking.
 */
public interface ShipmentTrackingService {

    /**
     * Save a shipmentTracking.
     *
     * @param shipmentTrackingDTO the entity to save
     * @return the persisted entity
     */
    ShipmentTrackingDTO save(ShipmentTrackingDTO shipmentTrackingDTO);

    /**
     * Get all the shipmentTrackings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentTrackingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" shipmentTracking.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentTrackingDTO> findOne(Long id);

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
    //Page<ShipmentTrackingDTO> search(String query, Pageable pageable);
    
    List<ShipmentTrackingDTO> searchConsignment(String query, Long vendorId);
    
    ShipmentInfoDTO searchConsignmentPublic(String query);
    
    ShipmentTrackingDTO autoFillNewTracking(String query, Long vendorId);
}
