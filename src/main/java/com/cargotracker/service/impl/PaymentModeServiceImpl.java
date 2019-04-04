package com.cargotracker.service.impl;

import com.cargotracker.service.PaymentModeService;
import com.cargotracker.domain.PaymentMode;
import com.cargotracker.repository.PaymentModeRepository;
//import com.cargotracker.repository.search.PaymentModeSearchRepository;
import com.cargotracker.service.dto.PaymentModeDTO;
import com.cargotracker.service.mapper.PaymentModeMapper;
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
 * Service Implementation for managing PaymentMode.
 */
@Service
@Transactional
public class PaymentModeServiceImpl implements PaymentModeService {

    private final Logger log = LoggerFactory.getLogger(PaymentModeServiceImpl.class);

    private final PaymentModeRepository paymentModeRepository;

    private final PaymentModeMapper paymentModeMapper;

    //private final PaymentModeSearchRepository paymentModeSearchRepository;

    public PaymentModeServiceImpl(PaymentModeRepository paymentModeRepository, PaymentModeMapper paymentModeMapper
    		//, PaymentModeSearchRepository paymentModeSearchRepository
    		) {
        this.paymentModeRepository = paymentModeRepository;
        this.paymentModeMapper = paymentModeMapper;
        //this.paymentModeSearchRepository = paymentModeSearchRepository;
    }

    /**
     * Save a paymentMode.
     *
     * @param paymentModeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentModeDTO save(PaymentModeDTO paymentModeDTO) {
        log.debug("Request to save PaymentMode : {}", paymentModeDTO);
        PaymentMode paymentMode = paymentModeMapper.toEntity(paymentModeDTO);
        paymentMode = paymentModeRepository.save(paymentMode);
        PaymentModeDTO result = paymentModeMapper.toDto(paymentMode);
        //paymentModeSearchRepository.save(paymentMode);
        result.setVendorname(paymentMode.getVendor().getVendorname());
        return result;
    }

    /**
     * Get all the paymentModes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentModeDTO> findAll() {
        log.debug("Request to get all PaymentModes");
        return paymentModeRepository.findAll().stream()
            .map(paymentModeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one paymentMode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentModeDTO> findOne(Long id) {
        log.debug("Request to get PaymentMode : {}", id);
        return paymentModeRepository.findById(id)
            .map(paymentModeMapper::toDto);
    }

    /**
     * Delete the paymentMode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentMode : {}", id);
        paymentModeRepository.deleteById(id);
        //paymentModeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentMode corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    /*@Override
    @Transactional(readOnly = true)
    public List<PaymentModeDTO> search(String query) {
        log.debug("Request to search PaymentModes for query {}", query);
        return StreamSupport
            .stream(paymentModeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(paymentModeMapper::toDto)
            .collect(Collectors.toList());
    }*/
}
