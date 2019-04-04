package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentTrackingService;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.repository.ShipmentTrackingRepository;
//import com.cargotracker.repository.search.ShipmentTrackingSearchRepository;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShipmentTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentTracking.
 */
@Service
@Transactional
public class ShipmentTrackingServiceImpl implements ShipmentTrackingService {

    private final Logger log = LoggerFactory.getLogger(ShipmentTrackingServiceImpl.class);

    private final ShipmentTrackingRepository shipmentTrackingRepository;

    private final ShipmentTrackingMapper shipmentTrackingMapper;

    //private final ShipmentTrackingSearchRepository shipmentTrackingSearchRepository;

    public ShipmentTrackingServiceImpl(ShipmentTrackingRepository shipmentTrackingRepository, ShipmentTrackingMapper shipmentTrackingMapper
    		//, ShipmentTrackingSearchRepository shipmentTrackingSearchRepository
    		) {
        this.shipmentTrackingRepository = shipmentTrackingRepository;
        this.shipmentTrackingMapper = shipmentTrackingMapper;
        //this.shipmentTrackingSearchRepository = shipmentTrackingSearchRepository;
    }

    /**
     * Save a shipmentTracking.
     *
     * @param shipmentTrackingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentTrackingDTO save(ShipmentTrackingDTO shipmentTrackingDTO) {
        log.debug("Request to save ShipmentTracking : {}", shipmentTrackingDTO);
        ShipmentTracking shipmentTracking = shipmentTrackingMapper.toEntity(shipmentTrackingDTO);
        shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
        ShipmentTrackingDTO result = shipmentTrackingMapper.toDto(shipmentTracking);
        //shipmentTrackingSearchRepository.save(shipmentTracking);
        return result;
    }

    /**
     * Get all the shipmentTrackings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentTrackingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentTrackings");
        return shipmentTrackingRepository.findAll(pageable)
            .map(shipmentTrackingMapper::toDto);
    }


    /**
     * Get one shipmentTracking by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentTrackingDTO> findOne(Long id) {
        log.debug("Request to get ShipmentTracking : {}", id);
        return shipmentTrackingRepository.findById(id)
            .map(shipmentTrackingMapper::toDto);
    }

    /**
     * Delete the shipmentTracking by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentTracking : {}", id);
        shipmentTrackingRepository.deleteById(id);
        //shipmentTrackingSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentTracking corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    /*@Override
    @Transactional(readOnly = true)
    public Page<ShipmentTrackingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShipmentTrackings for query {}", query);
        return shipmentTrackingSearchRepository.search(queryStringQuery(query), pageable)
            .map(shipmentTrackingMapper::toDto);
    }*/
}
