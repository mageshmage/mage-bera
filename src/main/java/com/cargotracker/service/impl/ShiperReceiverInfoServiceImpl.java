package com.cargotracker.service.impl;

import com.cargotracker.service.ShiperReceiverInfoService;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.search.ShiperReceiverInfoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShiperReceiverInfo.
 */
@Service
@Transactional
public class ShiperReceiverInfoServiceImpl implements ShiperReceiverInfoService {

    private final Logger log = LoggerFactory.getLogger(ShiperReceiverInfoServiceImpl.class);

    private final ShiperReceiverInfoRepository shiperReceiverInfoRepository;

    private final ShiperReceiverInfoSearchRepository shiperReceiverInfoSearchRepository;

    public ShiperReceiverInfoServiceImpl(ShiperReceiverInfoRepository shiperReceiverInfoRepository, ShiperReceiverInfoSearchRepository shiperReceiverInfoSearchRepository) {
        this.shiperReceiverInfoRepository = shiperReceiverInfoRepository;
        this.shiperReceiverInfoSearchRepository = shiperReceiverInfoSearchRepository;
    }

    /**
     * Save a shiperReceiverInfo.
     *
     * @param shiperReceiverInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public ShiperReceiverInfo save(ShiperReceiverInfo shiperReceiverInfo) {
        log.debug("Request to save ShiperReceiverInfo : {}", shiperReceiverInfo);
        ShiperReceiverInfo result = shiperReceiverInfoRepository.save(shiperReceiverInfo);
        shiperReceiverInfoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the shiperReceiverInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShiperReceiverInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ShiperReceiverInfos");
        return shiperReceiverInfoRepository.findAll(pageable);
    }


    /**
     * Get one shiperReceiverInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShiperReceiverInfo> findOne(Long id) {
        log.debug("Request to get ShiperReceiverInfo : {}", id);
        return shiperReceiverInfoRepository.findById(id);
    }

    /**
     * Delete the shiperReceiverInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShiperReceiverInfo : {}", id);
        shiperReceiverInfoRepository.deleteById(id);
        shiperReceiverInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the shiperReceiverInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShiperReceiverInfo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShiperReceiverInfos for query {}", query);
        return shiperReceiverInfoSearchRepository.search(queryStringQuery(query), pageable);    }
}
