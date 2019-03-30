package com.cargotracker.service.impl;

import com.cargotracker.service.CarrierDetailsService;
import com.cargotracker.domain.CarrierDetails;
import com.cargotracker.repository.CarrierDetailsRepository;
import com.cargotracker.repository.search.CarrierDetailsSearchRepository;
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
 * Service Implementation for managing CarrierDetails.
 */
@Service
@Transactional
public class CarrierDetailsServiceImpl implements CarrierDetailsService {

    private final Logger log = LoggerFactory.getLogger(CarrierDetailsServiceImpl.class);

    private final CarrierDetailsRepository carrierDetailsRepository;

    private final CarrierDetailsSearchRepository carrierDetailsSearchRepository;

    public CarrierDetailsServiceImpl(CarrierDetailsRepository carrierDetailsRepository, CarrierDetailsSearchRepository carrierDetailsSearchRepository) {
        this.carrierDetailsRepository = carrierDetailsRepository;
        this.carrierDetailsSearchRepository = carrierDetailsSearchRepository;
    }

    /**
     * Save a carrierDetails.
     *
     * @param carrierDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public CarrierDetails save(CarrierDetails carrierDetails) {
        log.debug("Request to save CarrierDetails : {}", carrierDetails);
        CarrierDetails result = carrierDetailsRepository.save(carrierDetails);
        carrierDetailsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the carrierDetails.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarrierDetails> findAll() {
        log.debug("Request to get all CarrierDetails");
        return carrierDetailsRepository.findAll();
    }


    /**
     * Get one carrierDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarrierDetails> findOne(Long id) {
        log.debug("Request to get CarrierDetails : {}", id);
        return carrierDetailsRepository.findById(id);
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
        carrierDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the carrierDetails corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarrierDetails> search(String query) {
        log.debug("Request to search CarrierDetails for query {}", query);
        return StreamSupport
            .stream(carrierDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
