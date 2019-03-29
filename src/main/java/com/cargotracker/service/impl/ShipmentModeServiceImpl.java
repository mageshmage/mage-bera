package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentModeService;
import com.cargotracker.domain.ShipmentMode;
import com.cargotracker.repository.ShipmentModeRepository;
import com.cargotracker.repository.search.ShipmentModeSearchRepository;
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
 * Service Implementation for managing ShipmentMode.
 */
@Service
@Transactional
public class ShipmentModeServiceImpl implements ShipmentModeService {

    private final Logger log = LoggerFactory.getLogger(ShipmentModeServiceImpl.class);

    private final ShipmentModeRepository shipmentModeRepository;

    private final ShipmentModeSearchRepository shipmentModeSearchRepository;

    public ShipmentModeServiceImpl(ShipmentModeRepository shipmentModeRepository, ShipmentModeSearchRepository shipmentModeSearchRepository) {
        this.shipmentModeRepository = shipmentModeRepository;
        this.shipmentModeSearchRepository = shipmentModeSearchRepository;
    }

    /**
     * Save a shipmentMode.
     *
     * @param shipmentMode the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentMode save(ShipmentMode shipmentMode) {
        log.debug("Request to save ShipmentMode : {}", shipmentMode);
        ShipmentMode result = shipmentModeRepository.save(shipmentMode);
        shipmentModeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shipmentModes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentMode> findAll() {
        log.debug("Request to get all ShipmentModes");
        return shipmentModeRepository.findAll();
    }


    /**
     * Get one shipmentMode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentMode> findOne(Long id) {
        log.debug("Request to get ShipmentMode : {}", id);
        return shipmentModeRepository.findById(id);
    }

    /**
     * Delete the shipmentMode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentMode : {}", id);
        shipmentModeRepository.deleteById(id);
        shipmentModeSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentMode corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentMode> search(String query) {
        log.debug("Request to search ShipmentModes for query {}", query);
        return StreamSupport
            .stream(shipmentModeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
