package com.cargotracker.service.impl;

import com.cargotracker.service.ShipmentInfoPODService;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.domain.Vendor;
import com.cargotracker.repository.ShipmentInfoPODRepository;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.VendorRepository;
//import com.cargotracker.repository.search.ShipmentInfoPODSearchRepository;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShipmentInfoPODMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ShipmentInfoPOD.
 */
@Service
@Transactional
public class ShipmentInfoPODServiceImpl implements ShipmentInfoPODService {

	private final Logger log = LoggerFactory.getLogger(ShipmentInfoPODServiceImpl.class);

	private final ShipmentInfoPODRepository shipmentInfoPODRepository;

	private final ShipmentInfoRepository shipmentInfoRepository;

	private final VendorRepository vendorRepository;

	private final ShipmentInfoPODMapper shipmentInfoPODMapper;

	// private final ShipmentInfoPODSearchRepository
	// shipmentInfoPODSearchRepository;

	public ShipmentInfoPODServiceImpl(ShipmentInfoPODRepository shipmentInfoPODRepository,
			ShipmentInfoPODMapper shipmentInfoPODMapper, ShipmentInfoRepository shipmentInfoRepository,
			VendorRepository vendorRepository
	// , ShipmentInfoPODSearchRepository shipmentInfoPODSearchRepository
	) {
		this.shipmentInfoPODRepository = shipmentInfoPODRepository;
		this.shipmentInfoRepository = shipmentInfoRepository;
		this.vendorRepository = vendorRepository;
		this.shipmentInfoPODMapper = shipmentInfoPODMapper;
		// this.shipmentInfoPODSearchRepository = shipmentInfoPODSearchRepository;
	}

	/**
	 * Save a shipmentInfoPOD.
	 *
	 * @param shipmentInfoPODDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ShipmentInfoPODDTO save(ShipmentInfoPODDTO shipmentInfoPODDTO) {
		log.debug("Request to save ShipmentInfoPOD : {}", shipmentInfoPODDTO);
		ShipmentInfoPOD shipmentInfoPOD = shipmentInfoPODMapper.toEntity(shipmentInfoPODDTO);

		if (shipmentInfoPODDTO.getVendorId() != null) {
			Vendor vendor = vendorRepository.findById(shipmentInfoPODDTO.getVendorId()).get();

			List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository
					.findByConsignmentNoAndVendor(shipmentInfoPODDTO.getSearchValue(), vendor);

			shipmentInfoPOD.setShipmentInfo(shipmentInfoList.get(0));
		}
		shipmentInfoPOD = shipmentInfoPODRepository.save(shipmentInfoPOD);
		ShipmentInfoPODDTO result = shipmentInfoPODMapper.toDto(shipmentInfoPOD);
		// shipmentInfoPODSearchRepository.save(shipmentInfoPOD);
		return result;
	}

	/**
	 * Get all the shipmentInfoPODS.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ShipmentInfoPODDTO> findAll() {
		log.debug("Request to get all ShipmentInfoPODS");
		return shipmentInfoPODRepository.findAll().stream().map(shipmentInfoPODMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one shipmentInfoPOD by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ShipmentInfoPODDTO> findOne(Long id) {
		log.debug("Request to get ShipmentInfoPOD : {}", id);
		return shipmentInfoPODRepository.findById(id).map(shipmentInfoPODMapper::toDto);
	}

	/**
	 * Delete the shipmentInfoPOD by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ShipmentInfoPOD : {}", id);
		shipmentInfoPODRepository.deleteById(id);
		// shipmentInfoPODSearchRepository.deleteById(id);
	}

	/**
	 * Search for the shipmentInfoPOD corresponding to the query.
	 *
	 * @param query
	 *            the query of the search
	 * @return the list of entities
	 * @throws Exception 
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<ShipmentInfoPODDTO> search(String
	 * query) { log.debug("Request to search ShipmentInfoPODS for query {}", query);
	 * return StreamSupport
	 * .stream(shipmentInfoPODSearchRepository.search(queryStringQuery(query)).
	 * spliterator(), false) .map(shipmentInfoPODMapper::toDto)
	 * .collect(Collectors.toList()); }
	 */

	@Override
	@Transactional(readOnly = true)
	public List<ShipmentInfoPODDTO> searchShipmentInfoPOD(String query, Long vendorId) throws Exception {
		log.debug("Request to search ShipmentInfoPODS for query {}", query);

		List<ShipmentInfoPODDTO> shipmentInfoPODDTOList = new ArrayList<ShipmentInfoPODDTO>();
		try {
		Vendor vendor = vendorRepository.findById(vendorId).get();

		List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(query, vendor);
		if (shipmentInfoList.size() > 1 || shipmentInfoList.size() == 0) {
			throw new Exception("Not Found");
		}

		List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository
				.findByShipmentInfo(shipmentInfoList.get(0));

		ShipmentInfoPODDTO shipmentInfoPODDTO = null;
		for (ShipmentInfoPOD shipmentInfoPOD : shipmentInfoPODList) {
			shipmentInfoPODDTO = new ShipmentInfoPODDTO();

			BeanUtils.copyProperties(shipmentInfoPOD, shipmentInfoPODDTO);
			shipmentInfoPODDTO.setShipmentInfoId(shipmentInfoList.get(0).getId());
			shipmentInfoPODDTOList.add(shipmentInfoPODDTO);
		}
		}
		catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}

		return shipmentInfoPODDTOList;
	}
}
