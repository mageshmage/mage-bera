package com.cargotracker.service.impl;

import com.cargotracker.service.StateService;
import com.cargotracker.domain.State;
import com.cargotracker.repository.StateRepository;
import com.cargotracker.repository.search.StateSearchRepository;
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
 * Service Implementation for managing State.
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    private final StateRepository stateRepository;

    private final StateSearchRepository stateSearchRepository;

    public StateServiceImpl(StateRepository stateRepository, StateSearchRepository stateSearchRepository) {
        this.stateRepository = stateRepository;
        this.stateSearchRepository = stateSearchRepository;
    }

    /**
     * Save a state.
     *
     * @param state the entity to save
     * @return the persisted entity
     */
    @Override
    public State save(State state) {
        log.debug("Request to save State : {}", state);
        State result = stateRepository.save(state);
        stateSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the states.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<State> findAll() {
        log.debug("Request to get all States");
        return stateRepository.findAll();
    }


    /**
     * Get one state by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<State> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
        stateSearchRepository.deleteById(id);
    }

    /**
     * Search for the state corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<State> search(String query) {
        log.debug("Request to search States for query {}", query);
        return StreamSupport
            .stream(stateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
