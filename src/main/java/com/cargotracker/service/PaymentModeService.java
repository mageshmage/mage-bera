package com.cargotracker.service;

import com.cargotracker.service.dto.PaymentModeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PaymentMode.
 */
public interface PaymentModeService {

    /**
     * Save a paymentMode.
     *
     * @param paymentModeDTO the entity to save
     * @return the persisted entity
     */
    PaymentModeDTO save(PaymentModeDTO paymentModeDTO);

    /**
     * Get all the paymentModes.
     *
     * @return the list of entities
     */
    List<PaymentModeDTO> findAll();


    /**
     * Get the "id" paymentMode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PaymentModeDTO> findOne(Long id);

    /**
     * Delete the "id" paymentMode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the paymentMode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PaymentModeDTO> search(String query);
}
