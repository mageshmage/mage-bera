package com.cargotracker.service;

import com.cargotracker.domain.PaymentMode;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PaymentMode.
 */
public interface PaymentModeService {

    /**
     * Save a paymentMode.
     *
     * @param paymentMode the entity to save
     * @return the persisted entity
     */
    PaymentMode save(PaymentMode paymentMode);

    /**
     * Get all the paymentModes.
     *
     * @return the list of entities
     */
    List<PaymentMode> findAll();


    /**
     * Get the "id" paymentMode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PaymentMode> findOne(Long id);

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
    List<PaymentMode> search(String query);
}
