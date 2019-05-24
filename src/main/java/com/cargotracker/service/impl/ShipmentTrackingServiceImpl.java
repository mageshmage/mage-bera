package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentTrackingService;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.domain.Vendor;
import com.cargotracker.domain.enumeration.ShiperReceiverType;
import com.cargotracker.repository.ShipmentInfoPODRepository;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.ShipmentTrackingRepository;
import com.cargotracker.repository.VendorRepository;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;
//import com.cargotracker.repository.search.ShipmentTrackingSearchRepository;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShipmentTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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
 * Service Implementation for managing ShipmentTracking.
 */
@Service
@Transactional
public class ShipmentTrackingServiceImpl implements ShipmentTrackingService {

	private final Logger log = LoggerFactory.getLogger(ShipmentTrackingServiceImpl.class);

	private final ShipmentTrackingRepository shipmentTrackingRepository;

	private final ShipmentInfoRepository shipmentInfoRepository;

	private final VendorRepository vendorRepository;

	private final ShipmentTrackingMapper shipmentTrackingMapper;

	private final ShipmentInfoPODRepository shipmentInfoPODRepository;

	// private final ShipmentTrackingSearchRepository
	// shipmentTrackingSearchRepository;

	public ShipmentTrackingServiceImpl(ShipmentTrackingRepository shipmentTrackingRepository,
			ShipmentTrackingMapper shipmentTrackingMapper, ShipmentInfoRepository shipmentInfoRepository,
			VendorRepository vendorRepository, ShipmentInfoPODRepository shipmentInfoPODRepository
	// , ShipmentTrackingSearchRepository shipmentTrackingSearchRepository
	) {
		this.shipmentTrackingRepository = shipmentTrackingRepository;
		this.shipmentInfoRepository = shipmentInfoRepository;
		this.vendorRepository = vendorRepository;
		this.shipmentTrackingMapper = shipmentTrackingMapper;
		this.shipmentInfoPODRepository = shipmentInfoPODRepository;
		// this.shipmentTrackingSearchRepository = shipmentTrackingSearchRepository;
	}

	/**
	 * Save a shipmentTracking.
	 *
	 * @param shipmentTrackingDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ShipmentTrackingDTO save(ShipmentTrackingDTO shipmentTrackingDTO) {
		log.debug("Request to save ShipmentTracking : {}", shipmentTrackingDTO);
		ShipmentTracking shipmentTracking = shipmentTrackingMapper.toEntity(shipmentTrackingDTO);
		shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
		ShipmentTrackingDTO result = shipmentTrackingMapper.toDto(shipmentTracking);
		// shipmentTrackingSearchRepository.save(shipmentTracking);

		ZoneId india = ZoneId.of("Asia/Kolkata");
		ShipmentInfo shipmentInfo = shipmentInfoRepository.findById(shipmentTrackingDTO.getShipmentInfoId()).get();
		
		if(shipmentTrackingDTO.getIsDelivered()) {
			shipmentInfo.setDeliveredDate(ZonedDateTime.now(india));
			shipmentInfo.setIsDelivered(true);
			shipmentInfo.setIsInTransit(true);
			shipmentInfo.setIsReachedNearestHub(true);
			shipmentInfo.setIsOutForDelivery(true);
			shipmentInfo.setReceivedBy(shipmentTrackingDTO.getReceivedBy());
			shipmentInfo.setRelationShip(shipmentTrackingDTO.getRelationShip());
		}
		else {
			shipmentInfo.setDeliveredDate(null);
			shipmentInfo.setIsDelivered(false);
			shipmentInfo.setIsInTransit(shipmentTrackingDTO.getIsInTransit());
			shipmentInfo.setIsReachedNearestHub(shipmentTrackingDTO.getIsReachedNearestHub());
			shipmentInfo.setIsOutForDelivery(shipmentTrackingDTO.getIsOutForDelivery());
			shipmentInfo.setReceivedBy(null);
			shipmentInfo.setRelationShip(null);
		}
		
		shipmentInfoRepository.save(shipmentInfo);

		return result;
	}

	/**
	 * Get all the shipmentTrackings.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ShipmentTrackingDTO> findAll(Pageable pageable) {
		log.debug("Request to get all ShipmentTrackings");
		return shipmentTrackingRepository.findAll(pageable).map(shipmentTrackingMapper::toDto);
	}

	/**
	 * Get one shipmentTracking by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ShipmentTrackingDTO> findOne(Long id) {
		log.debug("Request to get ShipmentTracking : {}", id);
		return shipmentTrackingRepository.findById(id).map(shipmentTrackingMapper::toDto);
	}

	/**
	 * Delete the shipmentTracking by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ShipmentTracking : {}", id);
		shipmentTrackingRepository.deleteById(id);
		// shipmentTrackingSearchRepository.deleteById(id);
	}

	/**
	 * Search for the shipmentTracking corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Page<ShipmentTrackingDTO>
	 * search(String query, Pageable pageable) {
	 * log.debug("Request to search for a page of ShipmentTrackings for query {}",
	 * query); return
	 * shipmentTrackingSearchRepository.search(queryStringQuery(query), pageable)
	 * .map(shipmentTrackingMapper::toDto); }
	 */

	@Override
	@Transactional(readOnly = true)
	public List<ShipmentTrackingDTO> searchConsignment(String query, Long vendorId) {
		log.debug("Request to search for a page of ShipmentTrackings for query {}", query);

		List<ShipmentTrackingDTO> shipmentTrackingDTOList = new ArrayList<ShipmentTrackingDTO>();

		Vendor vendor = vendorRepository.findById(vendorId).get();

		List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(query, vendor);
		if (shipmentInfoList.size() > 1 || shipmentInfoList.size() == 0)
			return shipmentTrackingDTOList;

		List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository
				.findByShipmentInfo(shipmentInfoList.get(0));

		ShipmentInfo shipmentInfo = shipmentInfoList.get(0);

		ShipmentTrackingDTO shipmentTrackingDTO = null;
		for (ShipmentTracking shipmentTracking : shipmentTrackingList) {
			shipmentTrackingDTO = new ShipmentTrackingDTO();

			BeanUtils.copyProperties(shipmentTracking, shipmentTrackingDTO);
			shipmentTrackingDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());
			shipmentTrackingDTO
					.setIsDelivered(shipmentInfo.getIsDelivered() != null ? shipmentInfo.getIsDelivered() : false);
			shipmentTrackingDTO
					.setIsInTransit(shipmentInfo.getIsInTransit() != null ? shipmentInfo.getIsInTransit() : false);
			shipmentTrackingDTO.setIsReachedNearestHub(
					shipmentInfo.getIsReachedNearestHub() != null ? shipmentInfo.getIsReachedNearestHub() : false);
			shipmentTrackingDTO.setReceivedBy(shipmentInfo.getReceivedBy());
			shipmentTrackingDTO.setRelationShip(shipmentInfo.getRelationShip());
			shipmentTrackingDTOList.add(shipmentTrackingDTO);
		}

		/*
		 * Iterator<ShipmentTracking> iterator =
		 * shipmentInfoList.get(0).getShipmentTrackings().iterator();
		 * ShipmentTrackingDTO shipmentTrackingDTO = null; while(iterator.hasNext()) {
		 * ShipmentTracking shipmentTracking = iterator.next(); shipmentTrackingDTO =
		 * new ShipmentTrackingDTO();
		 * 
		 * BeanUtils.copyProperties(shipmentTracking, shipmentTrackingDTO);
		 * shipmentTrackingDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());
		 * shipmentTrackingDTOList.add(shipmentTrackingDTO); }
		 */

		return shipmentTrackingDTOList;
	}

	@Override
	@Transactional(readOnly = true)
	public ShipmentInfoDTO searchConsignmentPublic(String query) {
		log.debug("Request to search for a page of searchConsignmentPublic for query {}", query);

		ShipmentInfoDTO shipmentInfoDTO = new ShipmentInfoDTO();

		List<ShipmentTrackingDTO> shipmentTrackingDTOList = new ArrayList<ShipmentTrackingDTO>();

		List<ShipmentInfoPODDTO> shipmentInfoPODDTOList = new ArrayList<ShipmentInfoPODDTO>();

		try {
			List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNo(query);

			if (shipmentInfoList.size() == 0) {
				throw new Exception("No Result");
			} else if (shipmentInfoList.size() > 2) {
				throw new Exception("Wrong Result");
			}

			ShipmentInfo shipmentInfo = shipmentInfoList.get(0);

			BeanUtils.copyProperties(shipmentInfo, shipmentInfoDTO);

			shipmentInfoDTO
					.setOriginValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
			shipmentInfoDTO.setDestinationValue(
					shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
			shipmentInfoDTO.setTrackingStatusValue(
					shipmentInfo.getTrackingStatus() != null ? shipmentInfo.getTrackingStatus().getValue() : null);
			shipmentInfoDTO
					.setIsDelivered(shipmentInfo.getIsDelivered() != null ? shipmentInfo.getIsDelivered() : false);
			shipmentInfoDTO
					.setIsInTransit(shipmentInfo.getIsInTransit() != null ? shipmentInfo.getIsInTransit() : false);
			shipmentInfoDTO.setIsReachedNearestHub(
					shipmentInfo.getIsReachedNearestHub() != null ? shipmentInfo.getIsReachedNearestHub() : false);
			shipmentInfoDTO.setIsOutForDelivery(shipmentInfo.getIsOutForDelivery() != null ? shipmentInfo.getIsOutForDelivery() : false);
			shipmentInfoDTO.setReceivedBy(shipmentInfo.getReceivedBy());
			shipmentInfoDTO.setRelationShip(shipmentInfo.getRelationShip());
			List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository
					.findByShipmentInfo(shipmentInfoList.get(0));

			ShipmentTrackingDTO shipmentTrackingDTO = null;
			for (ShipmentTracking shipmentTracking : shipmentTrackingList) {
				shipmentTrackingDTO = new ShipmentTrackingDTO();

				BeanUtils.copyProperties(shipmentTracking, shipmentTrackingDTO);
				shipmentTrackingDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());

				shipmentTrackingDTOList.add(shipmentTrackingDTO);
			}

			shipmentInfoDTO.setShipmentTrackings(shipmentTrackingDTOList);

			List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository
					.findByShipmentInfo(shipmentInfoList.get(0));

			ShipmentInfoPODDTO shipmentInfoPODDTO = null;
			for (ShipmentInfoPOD shipmentInfoPOD : shipmentInfoPODList) {
				shipmentInfoPODDTO = new ShipmentInfoPODDTO();

				BeanUtils.copyProperties(shipmentInfoPOD, shipmentInfoPODDTO);
				shipmentInfoPODDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());
				shipmentInfoPODDTOList.add(shipmentInfoPODDTO);
			}

			shipmentInfoDTO.setShipmentInfoPODs(shipmentInfoPODDTOList);

			Iterator<ShiperReceiverInfo> shipperReceiverIterator = shipmentInfo.getShipperReceiverInfos().iterator();

			ShiperReceiverInfoDTO shiperReceiverInfoDTO;
			while (shipperReceiverIterator.hasNext()) {
				ShiperReceiverInfo shiperReceiverInfo = shipperReceiverIterator.next();

				if (shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNOR) {
					shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
					// BeanUtils.copyProperties(shiperReceiverInfo, shiperReceiverInfoDTO);
					shiperReceiverInfoDTO.setCity(shiperReceiverInfo.getCity());
					// shiperReceiverInfoDTO.setShipmentInfoId(shipmentInfoDTO.getId());
					shipmentInfoDTO.setShipperInfo(shiperReceiverInfoDTO);
				} else if (shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNEE) {
					shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
					// BeanUtils.copyProperties(shiperReceiverInfo, shiperReceiverInfoDTO);
					// shiperReceiverInfoDTO.setShipmentInfoId(shipmentInfoDTO.getId());
					shiperReceiverInfoDTO.setCity(shiperReceiverInfo.getCity());
					shipmentInfoDTO.setReceiverInfo(shiperReceiverInfoDTO);
				}
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}

		return shipmentInfoDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ShipmentTrackingDTO autoFillNewTracking(String query, Long vendorId) {
		log.debug("Request to search for a page of ShipmentTrackings for query {}", query);

		//List<ShipmentTrackingDTO> shipmentTrackingDTOList = new ArrayList<ShipmentTrackingDTO>();
		ShipmentTrackingDTO shipmentTrackingDTO = new ShipmentTrackingDTO();
		ShipmentTracking shipmentTracking = null;

		Vendor vendor = vendorRepository.findById(vendorId).get();

		List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(query, vendor);
		if (shipmentInfoList.size() > 1 || shipmentInfoList.size() == 0) {
			// return shipmentTrackingDTOList;
			return shipmentTrackingDTO;
		}

		List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository
				.findByShipmentInfo(shipmentInfoList.get(0));

		ShipmentInfo shipmentInfo = shipmentInfoList.get(0);

		// ShipmentTrackingDTO shipmentTrackingDTO = null;

		shipmentTracking = shipmentTrackingList.get(0);
		shipmentTrackingDTO = new ShipmentTrackingDTO();

		BeanUtils.copyProperties(shipmentTracking, shipmentTrackingDTO);
		shipmentTrackingDTO.setId(null);
		shipmentTrackingDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());
		shipmentTrackingDTO
				.setIsDelivered(shipmentInfo.getIsDelivered() != null ? shipmentInfo.getIsDelivered() : false);
		shipmentTrackingDTO
				.setIsInTransit(shipmentInfo.getIsInTransit() != null ? shipmentInfo.getIsInTransit() : false);
		shipmentTrackingDTO.setIsReachedNearestHub(
				shipmentInfo.getIsReachedNearestHub() != null ? shipmentInfo.getIsReachedNearestHub() : false);
		shipmentTrackingDTO.setReceivedBy(shipmentInfo.getReceivedBy());
		shipmentTrackingDTO.setRelationShip(shipmentInfo.getRelationShip());
		//shipmentTrackingDTOList.add(shipmentTrackingDTO);

		return shipmentTrackingDTO;
	}
}
