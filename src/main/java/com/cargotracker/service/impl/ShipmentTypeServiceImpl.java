package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.domain.ShipmentType;
import com.cargotracker.repository.ShipmentTypeRepository;
import com.cargotracker.repository.search.ShipmentTypeSearchRepository;
import com.cargotracker.service.dto.ShipmentTypeDTO;
import com.cargotracker.service.mapper.ShipmentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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

    private final ShipmentTypeMapper shipmentTypeMapper;

    private final ShipmentTypeSearchRepository shipmentTypeSearchRepository;

    public ShipmentTypeServiceImpl(ShipmentTypeRepository shipmentTypeRepository, ShipmentTypeMapper shipmentTypeMapper, ShipmentTypeSearchRepository shipmentTypeSearchRepository) {
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.shipmentTypeMapper = shipmentTypeMapper;
        this.shipmentTypeSearchRepository = shipmentTypeSearchRepository;
    }

    /**
     * Save a shipmentType.
     *
     * @param shipmentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentTypeDTO save(ShipmentTypeDTO shipmentTypeDTO) {
        log.debug("Request to save ShipmentType : {}", shipmentTypeDTO);
        ShipmentType shipmentType = shipmentTypeMapper.toEntity(shipmentTypeDTO);
        shipmentType = shipmentTypeRepository.save(shipmentType);
        ShipmentTypeDTO result = shipmentTypeMapper.toDto(shipmentType);
        shipmentTypeSearchRepository.save(shipmentType);
        return result;
    }

    /**
     * Get all the shipmentTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentTypeDTO> findAll() {
        log.debug("Request to get all ShipmentTypes");
        return shipmentTypeRepository.findAll().stream()
            .map(shipmentTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shipmentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentTypeDTO> findOne(Long id) {
        log.debug("Request to get ShipmentType : {}", id);
        return shipmentTypeRepository.findById(id)
            .map(shipmentTypeMapper::toDto);
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
    public List<ShipmentTypeDTO> search(String query) {
        log.debug("Request to search ShipmentTypes for query {}", query);
        return StreamSupport
            .stream(shipmentTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shipmentTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
