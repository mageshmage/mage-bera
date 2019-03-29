package com.cargotracker.service.impl;

import com.cargotracker.service.TrackingStatusService;
import com.cargotracker.domain.TrackingStatus;
import com.cargotracker.repository.TrackingStatusRepository;
import com.cargotracker.repository.search.TrackingStatusSearchRepository;
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
 * Service Implementation for managing TrackingStatus.
 */
@Service
@Transactional
public class TrackingStatusServiceImpl implements TrackingStatusService {

    private final Logger log = LoggerFactory.getLogger(TrackingStatusServiceImpl.class);

    private final TrackingStatusRepository trackingStatusRepository;

    private final TrackingStatusSearchRepository trackingStatusSearchRepository;

    public TrackingStatusServiceImpl(TrackingStatusRepository trackingStatusRepository, TrackingStatusSearchRepository trackingStatusSearchRepository) {
        this.trackingStatusRepository = trackingStatusRepository;
        this.trackingStatusSearchRepository = trackingStatusSearchRepository;
    }

    /**
     * Save a trackingStatus.
     *
     * @param trackingStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public TrackingStatus save(TrackingStatus trackingStatus) {
        log.debug("Request to save TrackingStatus : {}", trackingStatus);
        TrackingStatus result = trackingStatusRepository.save(trackingStatus);
        trackingStatusSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the trackingStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackingStatus> findAll() {
        log.debug("Request to get all TrackingStatuses");
        return trackingStatusRepository.findAll();
    }


    /**
     * Get one trackingStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrackingStatus> findOne(Long id) {
        log.debug("Request to get TrackingStatus : {}", id);
        return trackingStatusRepository.findById(id);
    }

    /**
     * Delete the trackingStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrackingStatus : {}", id);
        trackingStatusRepository.deleteById(id);
        trackingStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the trackingStatus corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackingStatus> search(String query) {
        log.debug("Request to search TrackingStatuses for query {}", query);
        return StreamSupport
            .stream(trackingStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
