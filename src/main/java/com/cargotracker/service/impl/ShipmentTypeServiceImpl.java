package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.domain.ShipmentType;
import com.cargotracker.repository.ShipmentTypeRepository;
import com.cargotracker.repository.search.ShipmentTypeSearchRepository;
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
 * Service Implementation for managing ShipmentType.
 */
@Service
@Transactional
public class ShipmentTypeServiceImpl implements ShipmentTypeService {

    private final Logger log = LoggerFactory.getLogger(ShipmentTypeServiceImpl.class);

    private final ShipmentTypeRepository shipmentTypeRepository;

    private final ShipmentTypeSearchRepository shipmentTypeSearchRepository;

    public ShipmentTypeServiceImpl(ShipmentTypeRepository shipmentTypeRepository, ShipmentTypeSearchRepository shipmentTypeSearchRepository) {
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.shipmentTypeSearchRepository = shipmentTypeSearchRepository;
    }

    /**
     * Save a shipmentType.
     *
     * @param shipmentType the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentType save(ShipmentType shipmentType) {
        log.debug("Request to save ShipmentType : {}", shipmentType);
        ShipmentType result = shipmentTypeRepository.save(shipmentType);
        shipmentTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shipmentTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentType> findAll() {
        log.debug("Request to get all ShipmentTypes");
        return shipmentTypeRepository.findAll();
    }


    /**
     * Get one shipmentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentType> findOne(Long id) {
        log.debug("Request to get ShipmentType : {}", id);
        return shipmentTypeRepository.findById(id);
    }

    /**
     * Delete the shipmentType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentType : {}", id);
        shipmentTypeRepository.deleteById(id);
        shipmentTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentType> search(String query) {
        log.debug("Request to search ShipmentTypes for query {}", query);
        return StreamSupport
            .stream(shipmentTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
