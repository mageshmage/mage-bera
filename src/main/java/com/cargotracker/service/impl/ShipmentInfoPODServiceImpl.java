package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentInfoPODService;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.repository.ShipmentInfoPODRepository;
import com.cargotracker.repository.search.ShipmentInfoPODSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentInfoPOD.
 */
@Service
@Transactional
public class ShipmentInfoPODServiceImpl implements ShipmentInfoPODService {

    private final Logger log = LoggerFactory.getLogger(ShipmentInfoPODServiceImpl.class);

    private final ShipmentInfoPODRepository shipmentInfoPODRepository;

    private final ShipmentInfoPODSearchRepository shipmentInfoPODSearchRepository;

    public ShipmentInfoPODServiceImpl(ShipmentInfoPODRepository shipmentInfoPODRepository, ShipmentInfoPODSearchRepository shipmentInfoPODSearchRepository) {
        this.shipmentInfoPODRepository = shipmentInfoPODRepository;
        this.shipmentInfoPODSearchRepository = shipmentInfoPODSearchRepository;
    }

    /**
     * Save a shipmentInfoPOD.
     *
     * @param shipmentInfoPOD the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentInfoPOD save(ShipmentInfoPOD shipmentInfoPOD) {
        log.debug("Request to save ShipmentInfoPOD : {}", shipmentInfoPOD);
        ShipmentInfoPOD result = shipmentInfoPODRepository.save(shipmentInfoPOD);
        shipmentInfoPODSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shipmentInfoPODS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentInfoPOD> findAll() {
        log.debug("Request to get all ShipmentInfoPODS");
        return shipmentInfoPODRepository.findAll();
    }


    /**
     * Get one shipmentInfoPOD by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentInfoPOD> findOne(Long id) {
        log.debug("Request to get ShipmentInfoPOD : {}", id);
        return shipmentInfoPODRepository.findById(id);
    }

    /**
     * Delete the shipmentInfoPOD by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentInfoPOD : {}", id);
        shipmentInfoPODRepository.deleteById(id);
        shipmentInfoPODSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentInfoPOD corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentInfoPOD> search(String query) {
        log.debug("Request to search ShipmentInfoPODS for query {}", query);
        return StreamSupport
            .stream(shipmentInfoPODSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
