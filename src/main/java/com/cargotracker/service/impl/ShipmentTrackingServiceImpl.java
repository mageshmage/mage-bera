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
import com.cargotracker.service.dto.ExcelResponse;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;
//import com.cargotracker.repository.search.ShipmentTrackingSearchRepository;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShipmentTrackingMapper;
import com.cargotracker.service.util.HelperUtils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
		ShipmentTrackingDTO result = null;
		
		// shipmentTrackingSearchRepository.save(shipmentTracking);
		
		shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
		result = shipmentTrackingMapper.toDto(shipmentTracking);

		ZoneId india = ZoneId.of("Asia/Kolkata");
		ShipmentInfo shipmentInfo = shipmentInfoRepository.findById(shipmentTrackingDTO.getShipmentInfoId()).get();

		if (shipmentTrackingDTO.getIsDelivered() != null && shipmentTrackingDTO.getIsDelivered()) {
			/*if(shipmentInfo.getIsDelivered() == null || !shipmentInfo.getIsDelivered()) {
				shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
				result = shipmentTrackingMapper.toDto(shipmentTracking);
			}*/
			//shipmentInfo.setDeliveredDate(ZonedDateTime.now(india));
			shipmentInfo.setDeliveredDate(shipmentTracking.getTrackingDate());
			shipmentInfo.setIsDelivered(true);
			shipmentInfo.setIsInTransit(true);
			shipmentInfo.setIsReachedNearestHub(true);
			shipmentInfo.setIsOutForDelivery(true);
			shipmentInfo.setReceivedBy(shipmentTrackingDTO.getReceivedBy());
			shipmentInfo.setRelationShip(shipmentTrackingDTO.getRelationShip());
		} else {
			if (shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery()) {
				/*if(shipmentInfo.getIsOutForDelivery() == null || !shipmentInfo.getIsOutForDelivery()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}*/
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(true);
				shipmentInfo.setIsOutForDelivery(true);
			} else if (shipmentTrackingDTO.getIsReachedNearestHub() != null &&shipmentTrackingDTO.getIsReachedNearestHub()) {
				/*if(shipmentInfo.getIsReachedNearestHub() == null || !shipmentInfo.getIsReachedNearestHub()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}*/
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(true);
				shipmentInfo.setIsOutForDelivery(false);
			} else if (shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) {
				/*if(shipmentInfo.getIsInTransit() == null || !shipmentInfo.getIsInTransit()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}*/
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(false);
				shipmentInfo.setIsOutForDelivery(false);
			}
			shipmentInfo.setDeliveredDate(null);
			shipmentInfo.setIsDelivered(false);
			shipmentInfo.setReceivedBy(null);
			shipmentInfo.setRelationShip(null);
		}

		shipmentInfoRepository.save(shipmentInfo);

		return result;
	}
	
	@Override
	public ShipmentTrackingDTO saveBulk(ShipmentTrackingDTO shipmentTrackingDTO) {
		log.debug("Request to save ShipmentTracking : {}", shipmentTrackingDTO);
		ShipmentTracking shipmentTracking = shipmentTrackingMapper.toEntity(shipmentTrackingDTO);
		ShipmentTrackingDTO result = null;
		
		// shipmentTrackingSearchRepository.save(shipmentTracking);

		ZoneId india = ZoneId.of("Asia/Kolkata");
		ShipmentInfo shipmentInfo = shipmentInfoRepository.findById(shipmentTrackingDTO.getShipmentInfoId()).get();

		if (shipmentTrackingDTO.getIsDelivered() != null && shipmentTrackingDTO.getIsDelivered()) {
			if(shipmentInfo.getIsDelivered() == null || !shipmentInfo.getIsDelivered()) {
				shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
				result = shipmentTrackingMapper.toDto(shipmentTracking);
			}
			shipmentInfo.setDeliveredDate(ZonedDateTime.now(india));
			shipmentInfo.setIsDelivered(true);
			shipmentInfo.setIsInTransit(true);
			shipmentInfo.setIsReachedNearestHub(true);
			shipmentInfo.setIsOutForDelivery(true);
			shipmentInfo.setReceivedBy(shipmentTrackingDTO.getReceivedBy());
			shipmentInfo.setRelationShip(shipmentTrackingDTO.getRelationShip());
		} else {
			if (shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery()) {
				if(shipmentInfo.getIsOutForDelivery() == null || !shipmentInfo.getIsOutForDelivery()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(true);
				shipmentInfo.setIsOutForDelivery(true);
			} else if (shipmentTrackingDTO.getIsReachedNearestHub() != null &&shipmentTrackingDTO.getIsReachedNearestHub()) {
				if(shipmentInfo.getIsReachedNearestHub() == null || !shipmentInfo.getIsReachedNearestHub()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(true);
				shipmentInfo.setIsOutForDelivery(false);
			} else if (shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) {
				if(shipmentInfo.getIsInTransit() == null || !shipmentInfo.getIsInTransit()) {
					shipmentTracking = shipmentTrackingRepository.save(shipmentTracking);
					result = shipmentTrackingMapper.toDto(shipmentTracking);
				}
				shipmentInfo.setIsInTransit(true);
				shipmentInfo.setIsReachedNearestHub(false);
				shipmentInfo.setIsOutForDelivery(false);
			}
			shipmentInfo.setDeliveredDate(null);
			shipmentInfo.setIsDelivered(false);
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
	public ShipmentInfoDTO searchConsignmentPublic(String query, String vendorId) {
		log.debug("Request to search for a page of searchConsignmentPublic for query {}", query);

		ShipmentInfoDTO shipmentInfoDTO = new ShipmentInfoDTO();

		List<ShipmentTrackingDTO> shipmentTrackingDTOList = new ArrayList<ShipmentTrackingDTO>();

		List<ShipmentInfoPODDTO> shipmentInfoPODDTOList = new ArrayList<ShipmentInfoPODDTO>();
		Vendor vendor = null;

		try {
			List<ShipmentInfo> shipmentInfoList = null;
			if(vendorId != null && vendorId.trim().length() > 0 && Long.valueOf(vendorId) > 0) {
				vendor = vendorRepository.findById(Long.valueOf(vendorId)).get();
				shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(query, vendor);
			}
				
			else
				shipmentInfoList = shipmentInfoRepository.findByConsignmentNo(query);

			if (shipmentInfoList.size() == 0) {
				throw new Exception("No Result");
			} else if (shipmentInfoList.size() > 1) {
				throw new Exception("Wrong Result. Please provide Vendor");
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
			shipmentInfoDTO.setIsOutForDelivery(
					shipmentInfo.getIsOutForDelivery() != null ? shipmentInfo.getIsOutForDelivery() : false);
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

		// List<ShipmentTrackingDTO> shipmentTrackingDTOList = new
		// ArrayList<ShipmentTrackingDTO>();
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
		// shipmentTrackingDTOList.add(shipmentTrackingDTO);

		return shipmentTrackingDTO;
	}

	@Override
	public ExcelResponse createShipmentTrackingBulk(MultipartFile file, Long vendorId) throws IOException {

		// TODO Auto-generated method stub
		ExcelResponse excelResponse = new ExcelResponse();
		List<String> errorList = new ArrayList<String>();
		POIFSFileSystem fs = null;
		XSSFWorkbook wb = null;
		try {

			// Map<String, Long> shipmentTypeMap
			Set<String> consignmentNo = new HashSet<String>();

			// Vendor Managed Model
			Vendor vendor = vendorRepository.findById(vendorId).get();

			// Work Book
			wb = new XSSFWorkbook(file.getInputStream());

			List<ShipmentTrackingDTO> shipmentTrackingDTOList = new ArrayList<ShipmentTrackingDTO>();

			ShipmentTrackingDTO shipmentTrackingDTO = null;

			String pattern = "dd-MM-yyyy HH:mm";
			SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				if(i>0)
					break;
				XSSFSheet sheet = wb.getSheetAt(i);

				for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
					shipmentTrackingDTO = new ShipmentTrackingDTO();

					XSSFRow row = sheet.getRow(j);
					XSSFRow rowHeader = sheet.getRow(0);

					ShipmentInfo shipmentInfo = null;

					for (int z = row.getFirstCellNum(); z <= row.getLastCellNum(); z++) {
						Cell cell = row.getCell(z);
						if (cell != null)
							cell.setCellType(CellType.STRING);
						Cell cellHeader = rowHeader.getCell(z);
						if (z == 1) {
							// Consignment No
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								String consigmentNo = cell.getStringCellValue().trim();
								List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository
										.findByConsignmentNoAndVendor(consigmentNo, vendor);

								if (shipmentInfoList.size() == 0) {
									log.warn("Consignment No : " + consigmentNo
											+ " - is not Avaliable in our System - Sheet - " + sheet.getSheetName()
											+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue());
									errorList.add("Consignment No : " + consigmentNo
											+ " - is not Avaliable in our System - Sheet - " + sheet.getSheetName()
											+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue());
									break;
								} else {
									shipmentInfo = shipmentInfoList.get(0);
									consignmentNo.add(cell.getStringCellValue().trim());

								}
							} else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 2) {
							// Tracking Date
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								try {
								Date d;
								d = sDateFormat.parse(cell.getStringCellValue());

								shipmentTrackingDTO.setTrackingDate(
										ZonedDateTime.ofInstant(d.toInstant(), ZoneId.of("Asia/Kolkata")));
								} catch(Exception ex) {
									log.warn("Error - Date Format is not Correct. eg: 25-03-2018 12:30. Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							} else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 3) {
							// Place
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setPlace(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 4) {
							// Status
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setStatus(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 5) {
							// InTransit
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setIsInTransit(Boolean.parseBoolean(cell.getStringCellValue()));
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 6) {
							// Reached Nearest Hub
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO
										.setIsReachedNearestHub(Boolean.parseBoolean(cell.getStringCellValue()));
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 7) {
							// Out For Delivery
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO
										.setIsOutForDelivery(Boolean.parseBoolean(cell.getStringCellValue()));
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 8) {
							// Delivered
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setIsDelivered(Boolean.parseBoolean(cell.getStringCellValue()));
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 9) {
							// ReceivedBy
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setReceivedBy(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 10) {
							// Relationship
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipmentTrackingDTO.setRelationShip(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						}

					}
					
					if(shipmentInfo.getIsDelivered() != null && shipmentInfo.getIsDelivered()) {
						log.warn("Consignment No : " + shipmentInfo.getConsignmentNo()
								+ " - is Already Delivered - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						errorList.add("Consignment No : " + shipmentInfo.getConsignmentNo()
						+ " - is Already Delivered - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
					} else if(shipmentInfo.getIsOutForDelivery() != null && shipmentInfo.getIsOutForDelivery()) {
						if(shipmentTrackingDTO.getIsInTransit() == null
								|| !(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) 
								|| shipmentTrackingDTO.getIsReachedNearestHub() == null
								|| !(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub())
								|| shipmentTrackingDTO.getIsOutForDelivery() == null
								|| !(shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery())) {
							log.warn("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " - is Already Out For Delivery - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " - is Already Out For Delivery - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
					} else if(shipmentInfo.getIsReachedNearestHub() != null && shipmentInfo.getIsReachedNearestHub()) {
						if(shipmentTrackingDTO.getIsInTransit() == null
								|| !(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) 
								|| shipmentTrackingDTO.getIsReachedNearestHub() == null
								|| !(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub())) {
							log.warn("InTransit or ReachedNearesHub can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " - is Already Reached Nearest Hub - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("InTransit or ReachedNearesHub can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " - is Already Reached Nearest Hub - Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
					} else if(shipmentTrackingDTO.getIsDelivered() != null && shipmentTrackingDTO.getIsDelivered()) {
						if(shipmentTrackingDTO.getIsInTransit() == null
								|| !(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) 
								|| shipmentTrackingDTO.getIsReachedNearestHub() == null
								|| !(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub())
								|| shipmentTrackingDTO.getIsOutForDelivery() == null
								|| !(shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery())) {
							log.warn("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
						if(shipmentTrackingDTO.getReceivedBy() == null || shipmentTrackingDTO.getRelationShip() == null) {
							log.warn("If Delivered, ReceivedBy or Relationship cannot be null Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("If Delivered, ReceivedBy or Relationship cannot be null Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
					}
					else if(shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery()) {
						if(shipmentTrackingDTO.getIsInTransit() == null
								|| !(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) 
								|| shipmentTrackingDTO.getIsReachedNearestHub() == null
								|| !(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub())
								|| shipmentTrackingDTO.getIsOutForDelivery() == null
								|| !(shipmentTrackingDTO.getIsOutForDelivery() != null && shipmentTrackingDTO.getIsOutForDelivery())) {
							log.warn("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("InTransit or ReachedNearesHub or IsOutForDelivery can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
					} else if(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub()) {
						if(shipmentTrackingDTO.getIsInTransit() == null
								|| !(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) 
								|| shipmentTrackingDTO.getIsReachedNearestHub() == null
								|| !(shipmentTrackingDTO.getIsReachedNearestHub() != null && shipmentTrackingDTO.getIsReachedNearestHub())) {
							log.warn("InTransit or ReachedNearesHub can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
							errorList.add("InTransit or ReachedNearesHub can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
							+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						}
					} else if(shipmentTrackingDTO.getIsInTransit() != null && shipmentTrackingDTO.getIsInTransit()) {
					
					} else {
					
						log.warn("InTransit or ReachedNearesHub or IsOutForDelivery or IsDelivered can not be False or Empty. Conignment No : " + shipmentInfo.getConsignmentNo()
						+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
						errorList.add("InTransit or ReachedNearesHub or IsOutForDelivery or IsDelivered can not be False or Empty. Consignment No : " + shipmentInfo.getConsignmentNo()
						+ " . Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> ");
					}

					shipmentTrackingDTO.setShipmentInfoId(shipmentInfo.getId());
					shipmentTrackingDTOList.add(shipmentTrackingDTO);
				}
			}

			if (errorList.size() > 0) {
				return new ExcelResponse(true, errorList);
			}

			if (shipmentTrackingDTOList.size() > 0) {
				for (ShipmentTrackingDTO itr : shipmentTrackingDTOList) {
					saveBulk(itr);
				}
			}

			return new ExcelResponse(false, errorList);

		} catch (Exception ex) {
			log.error("Exception at Bulk Load - " + ex.getMessage());
		} finally {
			if (wb != null)
				wb.close();
			if (fs != null)
				fs.close();
		}
		return excelResponse;

	}
}
