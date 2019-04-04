package com.cargotracker.service;

import com.cargotracker.service.dto.ShiperReceiverInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ShiperReceiverInfo.
 */
public interface ShiperReceiverInfoService {

    /**
     * Save a shiperReceiverInfo.
     *
     * @param shiperReceiverInfoDTO the entity to save
     * @return the persisted entity
     */
    ShiperReceiverInfoDTO save(ShiperReceiverInfoDTO shiperReceiverInfoDTO);

    /**
     * Get all the shiperReceiverInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShiperReceiverInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" shiperReceiverInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShiperReceiverInfoDTO> findOne(Long id);

    /**
     * Delete the "id" shiperReceiverInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shiperReceiverInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    //Page<ShiperReceiverInfoDTO> search(String query, Pageable pageable);
}
