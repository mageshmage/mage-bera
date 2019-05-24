package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentInfoService;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.domain.enumeration.ShiperReceiverType;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.ShipmentInformationRepository;
import com.cargotracker.repository.ShipmentTrackingRepository;
import com.cargotracker.repository.StateRepository;
import com.cargotracker.repository.TrackingStatusRepository;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
//import com.cargotracker.repository.search.ShipmentInfoSearchRepository;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShiperReceiverInfoMapper;
import com.cargotracker.service.mapper.ShipmentInfoMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentInfo.
 */
@Service
@Transactional
public class ShipmentInfoServiceImpl implements ShipmentInfoService {

    private final Logger log = LoggerFactory.getLogger(ShipmentInfoServiceImpl.class);

    private final ShipmentInfoRepository shipmentInfoRepository;
    
    @Autowired
    private ShipmentInformationRepository shipmentInformationRepository;

    private final ShipmentInfoMapper shipmentInfoMapper;
    
    private final ShiperReceiverInfoRepository shiperReceiverInfoRepository;

    private final ShiperReceiverInfoMapper shiperReceiverInfoMapper;
    
    private final ShipmentTrackingRepository shipmentTrackingRepository;
    
    private final StateRepository stateRepository;
    
    private final TrackingStatusRepository trackingStatusRepository;

    //private final ShipmentInfoSearchRepository shipmentInfoSearchRepository;

    public ShipmentInfoServiceImpl(ShipmentInfoRepository shipmentInfoRepository, ShipmentInfoMapper shipmentInfoMapper,
    		ShiperReceiverInfoRepository shiperReceiverInfoRepository,
    		ShiperReceiverInfoMapper shiperReceiverInfoMapper,
    		ShipmentTrackingRepository shipmentTrackingRepository,
    		StateRepository stateRepository,
    		TrackingStatusRepository trackingStatusRepository
    		//, ShipmentInfoSearchRepository shipmentInfoSearchRepository
    		) {
        this.shipmentInfoRepository = shipmentInfoRepository;
        this.shipmentInfoMapper = shipmentInfoMapper;
        this.shiperReceiverInfoRepository = shiperReceiverInfoRepository;
        this.shiperReceiverInfoMapper = shiperReceiverInfoMapper;
        this.shipmentTrackingRepository = shipmentTrackingRepository;
        this.stateRepository = stateRepository;
        this.trackingStatusRepository = trackingStatusRepository;
        //this.shipmentInfoSearchRepository = shipmentInfoSearchRepository;
    }

    /**
     * Save a shipmentInfo.
     *
     * @param shipmentInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShipmentInfoDTO save(ShipmentInfoDTO shipmentInfoDTO) {
        log.debug("Request to save ShipmentInfo : {}", shipmentInfoDTO);
        ShipmentInfo shipmentInfo = shipmentInfoMapper.toEntity(shipmentInfoDTO);
        shipmentInfo = shipmentInfoRepository.save(shipmentInfo);
        ShipmentInfoDTO result = shipmentInfoMapper.toDto(shipmentInfo);
        
        result.setCarrierDetailsValue(shipmentInfo.getCarrierDetails() != null ? shipmentInfo.getCarrierDetails().getValue() : null);
        result.setShipmentTypeValue(shipmentInfo.getShipmentType() != null ? shipmentInfo.getShipmentType().getValue() : null);
        result.setShipmentModeValue(shipmentInfo.getShipmentMode() != null ? shipmentInfo.getShipmentMode().getValue() : null);
        result.setPaymentModeValue(shipmentInfo.getPaymentMode() != null ? shipmentInfo.getPaymentMode().getValue() : null);
        result.setTrackingStatusValue(shipmentInfo.getTrackingStatus() != null ? shipmentInfo.getTrackingStatus().getValue() : null);
        result.setVendorname(shipmentInfo.getVendor() != null ? shipmentInfo.getVendor().getVendorname() : null);
        result.setOriginValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
        result.setDestinationValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
        return result;
    }
    
    @Override
    public ShipmentInfoDTO saveShipmentInformation(ShipmentInfoDTO shipmentInfoDTO) {
        log.debug("Request to save ShipmentInformation : {}", shipmentInfoDTO);
        ShipmentInfo shipmentInfo = shipmentInfoMapper.toEntity(shipmentInfoDTO);
        shipmentInfo = shipmentInfoRepository.save(shipmentInfo);
        ShipmentInfoDTO result = shipmentInfoMapper.toDto(shipmentInfo);
        
        log.debug("Request to save ShiperInfo : {}", shipmentInfoDTO.getShipperInfo());
        ShiperReceiverInfo shiperInfo = shiperReceiverInfoMapper.toEntity(shipmentInfoDTO.getShipperInfo());
        shiperInfo.setType(ShiperReceiverType.CONSIGNOR);
        shiperInfo.setShipmentInfo(shipmentInfo);
        shiperInfo = shiperReceiverInfoRepository.save(shiperInfo);
        
        log.debug("Request to save ReceiverInfo : {}", shipmentInfoDTO.getReceiverInfo());
        ShiperReceiverInfo receiverInfo = shiperReceiverInfoMapper.toEntity(shipmentInfoDTO.getReceiverInfo());
        receiverInfo.setType(ShiperReceiverType.CONSIGNEE);
        receiverInfo.setShipmentInfo(shipmentInfo);
        receiverInfo = shiperReceiverInfoRepository.save(receiverInfo);
        
        ZoneId  india = ZoneId.of("Asia/Kolkata");
        ShipmentTracking shipmentTracking = new ShipmentTracking();
        if(shipmentInfoDTO.getId() == null) {
        	shipmentTracking.setPlace(stateRepository.findById(shipmentInfo.getOrigin().getId()).get().getStateName());
            shipmentTracking.setShipmentInfo(shipmentInfo);
            //shipmentTracking.setStatus(trackingStatusRepository.findById(shipmentInfo.getTrackingStatus().getId()).get().getValue());
            shipmentTracking.setStatus("Shipment Created");
            shipmentTracking.setTrackingDate(ZonedDateTime.now(india));
            
            shipmentTrackingRepository.save(shipmentTracking);
        }
        
        
        
        result.setCarrierDetailsValue(shipmentInfo.getCarrierDetails() != null ? shipmentInfo.getCarrierDetails().getValue() : null);
        result.setShipmentTypeValue(shipmentInfo.getShipmentType() != null ? shipmentInfo.getShipmentType().getValue() : null);
        result.setShipmentModeValue(shipmentInfo.getShipmentMode() != null ? shipmentInfo.getShipmentMode().getValue() : null);
        result.setPaymentModeValue(shipmentInfo.getPaymentMode() != null ? shipmentInfo.getPaymentMode().getValue() : null);
        result.setTrackingStatusValue(shipmentInfo.getTrackingStatus() != null ? shipmentInfo.getTrackingStatus().getValue() : null);
        result.setVendorname(shipmentInfo.getVendor() != null ? shipmentInfo.getVendor().getVendorname() : null);
        result.setOriginValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
        result.setDestinationValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
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
    public Page<ShipmentInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentInfos");
        return shipmentInfoRepository.findAll(pageable)
            .map(shipmentInfoMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ShipmentInfoDTO> findAllShipmentInformations(Pageable pageable, ShipmentInformationSearchDTO shipmentSearchDto) {
        log.debug("Request to get all ShipmentInformations");
        
        Page<ShipmentInfoDTO> shipmentInfoDTO = null;
		try {
			shipmentInfoDTO = shipmentInformationRepository.searchShipmentInfoQueryDSL(pageable, shipmentSearchDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*Page<ShipmentInfoDTO> shipmentInformationsDTO = shipmentInfoRepository.findAll(pageable)
        .map(shipmentInfoMapper::toDto);
        
        List<ShipmentInfoDTO> dTOs = shipmentInformationsDTO.getContent();
        
        for(ShipmentInfoDTO dto : dTOs) {
        	List<Long> ids = new ArrayList<Long>();
        	ids.add(dto.getId());
        	List<ShiperReceiverInfo> shiperReceiverInfoList =  shiperReceiverInfoRepository.findByIdIn(ids);
        	//Optional<ShiperReceiverInfo> shiperReceiverInfoOpt = shiperReceiverInfoRepository.findById(dto.getId());
        	//ShiperReceiverInfo shiperReceiverInfo = shiperReceiverInfoOpt.get();
        	
        	ShiperReceiverInfoDTO shiperReceiverInfoDTO = null;
        	for(ShiperReceiverInfo itr : shiperReceiverInfoList) {
        		if(itr.getType() == ShiperReceiverType.CONSIGNOR) {
        			shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
        			BeanUtils.copyProperties(itr, shiperReceiverInfoDTO);
        			shiperReceiverInfoDTO.setShipmentInfoId(dto.getId());
            		dto.setShipperInfo(shiperReceiverInfoDTO);
            	}
            	else if(itr.getType() == ShiperReceiverType.CONSIGNEE) {
            		shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
        			BeanUtils.copyProperties(itr, shiperReceiverInfoDTO);
        			shiperReceiverInfoDTO.setShipmentInfoId(dto.getId());
            		dto.setReceiverInfo(shiperReceiverInfoDTO);
            	}
        	}
        	
        }
        
        return new PageImpl<ShipmentInfoDTO>(dTOs, shipmentInformationsDTO.getPageable(), shipmentInformationsDTO.getTotalElements());*/
        return shipmentInfoDTO;
    }


    /**
     * Get one shipmentInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentInfoDTO> findOne(Long id) {
        log.debug("Request to get ShipmentInfo : {}", id);
        return shipmentInfoRepository.findById(id)
            .map(shipmentInfoMapper::toDto);
    }
    
	@Override
	@Transactional(readOnly = true)
	public ShipmentInfoDTO findOneShipmentInformation(Long id) {
		ShipmentInfoDTO shipmentInfoDTO = null;
		log.debug("Request to get ShipmentInfo : {}", id);
		Optional<ShipmentInfo> shipmentInfo = shipmentInfoRepository.findById(id);
		
		shipmentInfoDTO = shipmentInfo.map(shipmentInfoMapper::toDto).get();
    	
    	Iterator<ShiperReceiverInfo> shipperReceiverIterator = shipmentInfo.get().getShipperReceiverInfos().iterator();
    	ShiperReceiverInfoDTO shiperReceiverInfoDTO;
    	while(shipperReceiverIterator.hasNext()) {
    		ShiperReceiverInfo shiperReceiverInfo = shipperReceiverIterator.next();
    		
    		if(shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNOR) {
    			shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
    			BeanUtils.copyProperties(shiperReceiverInfo, shiperReceiverInfoDTO);
    			shiperReceiverInfoDTO.setShipmentInfoId(shipmentInfoDTO.getId());
    			shipmentInfoDTO.setShipperInfo(shiperReceiverInfoDTO);
        	}
        	else if(shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNEE) {
        		shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
    			BeanUtils.copyProperties(shiperReceiverInfo, shiperReceiverInfoDTO);
    			shiperReceiverInfoDTO.setShipmentInfoId(shipmentInfoDTO.getId());
    			shipmentInfoDTO.setReceiverInfo(shiperReceiverInfoDTO);
        	}
    	}
		
		return shipmentInfoDTO;
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
        //shipmentInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the shipmentInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    /*@Override
    @Transactional(readOnly = true)
    public Page<ShipmentInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShipmentInfos for query {}", query);
        return shipmentInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(shipmentInfoMapper::toDto);
    }*/
}
