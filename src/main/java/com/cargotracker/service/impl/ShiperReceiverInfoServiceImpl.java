package com.cargotracker.service.impl;

import com.cargotracker.service.ShiperReceiverInfoService;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.search.ShiperReceiverInfoSearchRepository;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
import com.cargotracker.service.mapper.ShiperReceiverInfoMapper;
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

    private final ShiperReceiverInfoMapper shiperReceiverInfoMapper;

    private final ShiperReceiverInfoSearchRepository shiperReceiverInfoSearchRepository;

    public ShiperReceiverInfoServiceImpl(ShiperReceiverInfoRepository shiperReceiverInfoRepository, ShiperReceiverInfoMapper shiperReceiverInfoMapper, ShiperReceiverInfoSearchRepository shiperReceiverInfoSearchRepository) {
        this.shiperReceiverInfoRepository = shiperReceiverInfoRepository;
        this.shiperReceiverInfoMapper = shiperReceiverInfoMapper;
        this.shiperReceiverInfoSearchRepository = shiperReceiverInfoSearchRepository;
    }

    /**
     * Save a shiperReceiverInfo.
     *
     * @param shiperReceiverInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShiperReceiverInfoDTO save(ShiperReceiverInfoDTO shiperReceiverInfoDTO) {
        log.debug("Request to save ShiperReceiverInfo : {}", shiperReceiverInfoDTO);
        ShiperReceiverInfo shiperReceiverInfo = shiperReceiverInfoMapper.toEntity(shiperReceiverInfoDTO);
        shiperReceiverInfo = shiperReceiverInfoRepository.save(shiperReceiverInfo);
        ShiperReceiverInfoDTO result = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);
        shiperReceiverInfoSearchRepository.save(shiperReceiverInfo);
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
    public Page<ShiperReceiverInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShiperReceiverInfos");
        return shiperReceiverInfoRepository.findAll(pageable)
            .map(shiperReceiverInfoMapper::toDto);
    }


    /**
     * Get one shiperReceiverInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShiperReceiverInfoDTO> findOne(Long id) {
        log.debug("Request to get ShiperReceiverInfo : {}", id);
        return shiperReceiverInfoRepository.findById(id)
            .map(shiperReceiverInfoMapper::toDto);
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
    public Page<ShiperReceiverInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShiperReceiverInfos for query {}", query);
        return shiperReceiverInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(shiperReceiverInfoMapper::toDto);
    }
}
