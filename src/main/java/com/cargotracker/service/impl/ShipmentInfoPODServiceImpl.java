package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentInfoPODService;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.repository.ShipmentInfoPODRepository;
//import com.cargotracker.repository.search.ShipmentInfoPODSearchRepository;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;
import com.cargotracker.service.mapper.ShipmentInfoPODMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentInfoPOD.
 */
@Service
@Transactional
public class ShipmentInfoPODServiceImpl implements ShipmentInfoPODService {

    private final Logger log = LoggerFactory.getLogger(ShipmentInfoPODServiceImpl.class);

    private final ShipmentInfoPODRepository shipmentInfoPODRepository;

    private final ShipmentInfoPODMapper shipmentInfoPODMapper;

    //private final ShipmentInfoPODSearchRepository shipmentInfoPODSearchRepository;

    public ShipmentInfoPODServiceImpl(ShipmentInfoPODRepository shipmentInfoPODRepository, ShipmentInfoPODMapper shipmentInfoPODMapper
    		//, ShipmentInfoPODSearchRepository shipmentInfoPODSearchRepository
    		) {
        this.shipmentInfoPODRepository = shipmentInfoPODRepository;
        this.shipmentInfoPODMapper = shipmentInfoPODMapper;
        //this.shipmentInfoPODSearchRepository = shipmentInfoPODSearchRepository;
    }

    /**
     * Save a shipmentInfoPOD.
     *
     * @param shipmentInfoPODDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentInfoPODDTO save(ShipmentInfoPODDTO shipmentInfoPODDTO) {
        log.debug("Request to save ShipmentInfoPOD : {}", shipmentInfoPODDTO);
        ShipmentInfoPOD shipmentInfoPOD = shipmentInfoPODMapper.toEntity(shipmentInfoPODDTO);
        shipmentInfoPOD = shipmentInfoPODRepository.save(shipmentInfoPOD);
        ShipmentInfoPODDTO result = shipmentInfoPODMapper.toDto(shipmentInfoPOD);
        //shipmentInfoPODSearchRepository.save(shipmentInfoPOD);
        return result;
    }

    /**
     * Get all the shipmentInfoPODS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentInfoPODDTO> findAll() {
        log.debug("Request to get all ShipmentInfoPODS");
        return shipmentInfoPODRepository.findAll().stream()
            .map(shipmentInfoPODMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipmentInfoPOD by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentInfoPODDTO> findOne(Long id) {
        log.debug("Request to get ShipmentInfoPOD : {}", id);
        return shipmentInfoPODRepository.findById(id)
            .map(shipmentInfoPODMapper::toDto);
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
        //shipmentInfoPODSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentInfoPOD corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    /*@Override
    @Transactional(readOnly = true)
    public List<ShipmentInfoPODDTO> search(String query) {
        log.debug("Request to search ShipmentInfoPODS for query {}", query);
        return StreamSupport
            .stream(shipmentInfoPODSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipmentInfoPODMapper::toDto)
            .collect(Collectors.toList());
    }*/
}
