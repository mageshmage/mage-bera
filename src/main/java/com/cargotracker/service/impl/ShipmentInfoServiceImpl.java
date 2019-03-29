package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentInfoService;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.search.ShipmentInfoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentInfo.
 */
@Service
@Transactional
public class ShipmentInfoServiceImpl implements ShipmentInfoService {

    private final Logger log = LoggerFactory.getLogger(ShipmentInfoServiceImpl.class);

    private final ShipmentInfoRepository shipmentInfoRepository;

    private final ShipmentInfoSearchRepository shipmentInfoSearchRepository;

    public ShipmentInfoServiceImpl(ShipmentInfoRepository shipmentInfoRepository, ShipmentInfoSearchRepository shipmentInfoSearchRepository) {
        this.shipmentInfoRepository = shipmentInfoRepository;
        this.shipmentInfoSearchRepository = shipmentInfoSearchRepository;
    }

    /**
     * Save a shipmentInfo.
     *
     * @param shipmentInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentInfo save(ShipmentInfo shipmentInfo) {
        log.debug("Request to save ShipmentInfo : {}", shipmentInfo);
        ShipmentInfo result = shipmentInfoRepository.save(shipmentInfo);
        shipmentInfoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shipmentInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentInfos");
        return shipmentInfoRepository.findAll(pageable);
    }


    /**
     * Get one shipmentInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentInfo> findOne(Long id) {
        log.debug("Request to get ShipmentInfo : {}", id);
        return shipmentInfoRepository.findById(id);
    }

    /**
     * Delete the shipmentInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentInfo : {}", id);
        shipmentInfoRepository.deleteById(id);
        shipmentInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentInfo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShipmentInfos for query {}", query);
        return shipmentInfoSearchRepository.search(queryStringQuery(query), pageable);    }
}
