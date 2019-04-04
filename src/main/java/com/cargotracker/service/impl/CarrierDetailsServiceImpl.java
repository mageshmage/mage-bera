package com.cargotracker.service.impl;

import com.cargotracker.service.CarrierDetailsService;
import com.cargotracker.domain.CarrierDetails;
import com.cargotracker.repository.CarrierDetailsRepository;
//import com.cargotracker.repository.search.CarrierDetailsSearchRepository;
import com.cargotracker.service.dto.CarrierDetailsDTO;
import com.cargotracker.service.mapper.CarrierDetailsMapper;
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
 * Service Implementation for managing CarrierDetails.
 */
@Service
@Transactional
public class CarrierDetailsServiceImpl implements CarrierDetailsService {

    private final Logger log = LoggerFactory.getLogger(CarrierDetailsServiceImpl.class);

    private final CarrierDetailsRepository carrierDetailsRepository;

    private final CarrierDetailsMapper carrierDetailsMapper;

    //private final CarrierDetailsSearchRepository carrierDetailsSearchRepository;

    public CarrierDetailsServiceImpl(CarrierDetailsRepository carrierDetailsRepository, CarrierDetailsMapper carrierDetailsMapper
    		//, CarrierDetailsSearchRepository carrierDetailsSearchRepository
    		) {
        this.carrierDetailsRepository = carrierDetailsRepository;
        this.carrierDetailsMapper = carrierDetailsMapper;
        //this.carrierDetailsSearchRepository = carrierDetailsSearchRepository;
    }

    /**
     * Save a carrierDetails.
     *
     * @param carrierDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarrierDetailsDTO save(CarrierDetailsDTO carrierDetailsDTO) {
        log.debug("Request to save CarrierDetails : {}", carrierDetailsDTO);
        CarrierDetails carrierDetails = carrierDetailsMapper.toEntity(carrierDetailsDTO);
        carrierDetails = carrierDetailsRepository.save(carrierDetails);
        CarrierDetailsDTO result = carrierDetailsMapper.toDto(carrierDetails);
        //carrierDetailsSearchRepository.save(carrierDetails);
        result.setVendorname(carrierDetails.getVendor().getVendorname());
        return result;
    }

    /**
     * Get all the carrierDetails.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarrierDetailsDTO> findAll() {
        log.debug("Request to get all CarrierDetails");
        return carrierDetailsRepository.findAll().stream()
            .map(carrierDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carrierDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarrierDetailsDTO> findOne(Long id) {
        log.debug("Request to get CarrierDetails : {}", id);
        return carrierDetailsRepository.findById(id)
            .map(carrierDetailsMapper::toDto);
    }

    /**
     * Delete the carrierDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarrierDetails : {}", id);
        carrierDetailsRepository.deleteById(id);
        //carrierDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the carrierDetails corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    /*@Override
    @Transactional(readOnly = true)
    public List<CarrierDetailsDTO> search(String query) {
        log.debug("Request to search CarrierDetails for query {}", query);
        return StreamSupport
            .stream(carrierDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(carrierDetailsMapper::toDto)
            .collect(Collectors.toList());
    }*/
}
