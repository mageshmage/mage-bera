package com.cargotracker.service;

import com.cargotracker.service.dto.CarrierDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CarrierDetails.
 */
public interface CarrierDetailsService {

    /**
     * Save a carrierDetails.
     *
     * @param carrierDetailsDTO the entity to save
     * @return the persisted entity
     */
    CarrierDetailsDTO save(CarrierDetailsDTO carrierDetailsDTO);

    /**
     * Get all the carrierDetails.
     *
     * @return the list of entities
     */
    List<CarrierDetailsDTO> findAll();


    /**
     * Get the "id" carrierDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CarrierDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" carrierDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the carrierDetails corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CarrierDetailsDTO> search(String query);
}
