package com.cargotracker.service.impl;

import com.cargotracker.service.CarrierDetailsService;
import com.cargotracker.service.PaymentModeService;
import com.cargotracker.service.ShipmentInfoService;
import com.cargotracker.service.ShipmentModeService;
import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.service.StateService;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.domain.Vendor;
import com.cargotracker.domain.enumeration.ShiperReceiverType;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.ShipmentInformationRepository;
import com.cargotracker.repository.ShipmentTrackingRepository;
import com.cargotracker.repository.StateRepository;
import com.cargotracker.repository.TrackingStatusRepository;
import com.cargotracker.repository.VendorRepository;
import com.cargotracker.service.dto.CarrierDetailsDTO;
import com.cargotracker.service.dto.ExcelResponse;
import com.cargotracker.service.dto.PaymentModeDTO;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
//import com.cargotracker.repository.search.ShipmentInfoSearchRepository;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;
import com.cargotracker.service.dto.ShipmentModeDTO;
import com.cargotracker.service.dto.ShipmentTypeDTO;
import com.cargotracker.service.dto.StateDTO;
import com.cargotracker.service.mapper.ShiperReceiverInfoMapper;
import com.cargotracker.service.mapper.ShipmentInfoMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
	
	@Autowired
	private VendorRepository vendorRepository;

	private final ShipmentInfoMapper shipmentInfoMapper;

	private final ShiperReceiverInfoRepository shiperReceiverInfoRepository;

	private final ShiperReceiverInfoMapper shiperReceiverInfoMapper;

	private final ShipmentTrackingRepository shipmentTrackingRepository;

	private final StateRepository stateRepository;

	private final TrackingStatusRepository trackingStatusRepository;

	@Autowired
	ShipmentTypeService shipmentTypeService;

	@Autowired
	ShipmentModeService shipmentModeService;

	@Autowired
	PaymentModeService paymentModeService;

	@Autowired
	CarrierDetailsService carrierDetailsService;

	@Autowired
	StateService stateService;

	// private final ShipmentInfoSearchRepository shipmentInfoSearchRepository;

	public ShipmentInfoServiceImpl(ShipmentInfoRepository shipmentInfoRepository, ShipmentInfoMapper shipmentInfoMapper,
			ShiperReceiverInfoRepository shiperReceiverInfoRepository,
			ShiperReceiverInfoMapper shiperReceiverInfoMapper, ShipmentTrackingRepository shipmentTrackingRepository,
			StateRepository stateRepository, TrackingStatusRepository trackingStatusRepository
	// , ShipmentInfoSearchRepository shipmentInfoSearchRepository
	) {
		this.shipmentInfoRepository = shipmentInfoRepository;
		this.shipmentInfoMapper = shipmentInfoMapper;
		this.shiperReceiverInfoRepository = shiperReceiverInfoRepository;
		this.shiperReceiverInfoMapper = shiperReceiverInfoMapper;
		this.shipmentTrackingRepository = shipmentTrackingRepository;
		this.stateRepository = stateRepository;
		this.trackingStatusRepository = trackingStatusRepository;
		// this.shipmentInfoSearchRepository = shipmentInfoSearchRepository;
	}

	/**
	 * Save a shipmentInfo.
	 *
	 * @param shipmentInfoDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ShipmentInfoDTO save(ShipmentInfoDTO shipmentInfoDTO) {
		log.debug("Request to save ShipmentInfo : {}", shipmentInfoDTO);
		ShipmentInfo shipmentInfo = shipmentInfoMapper.toEntity(shipmentInfoDTO);
		if(shipmentInfo.isIsThirdParty() == null)
			shipmentInfo.setIsThirdParty(false);
		shipmentInfo = shipmentInfoRepository.save(shipmentInfo);
		ShipmentInfoDTO result = shipmentInfoMapper.toDto(shipmentInfo);

		result.setCarrierDetailsValue(
				shipmentInfo.getCarrierDetails() != null ? shipmentInfo.getCarrierDetails().getValue() : null);
		result.setShipmentTypeValue(
				shipmentInfo.getShipmentType() != null ? shipmentInfo.getShipmentType().getValue() : null);
		result.setShipmentModeValue(
				shipmentInfo.getShipmentMode() != null ? shipmentInfo.getShipmentMode().getValue() : null);
		result.setPaymentModeValue(
				shipmentInfo.getPaymentMode() != null ? shipmentInfo.getPaymentMode().getValue() : null);
		result.setTrackingStatusValue(
				shipmentInfo.getTrackingStatus() != null ? shipmentInfo.getTrackingStatus().getValue() : null);
		result.setVendorname(shipmentInfo.getVendor() != null ? shipmentInfo.getVendor().getVendorname() : null);
		result.setOriginValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
		result.setDestinationValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
		return result;
	}

	@Override
	public ShipmentInfoDTO saveShipmentInformation(ShipmentInfoDTO shipmentInfoDTO) throws Exception {
		log.debug("Request to save ShipmentInformation : {}", shipmentInfoDTO);
		
		ShipmentInfo shipmentInfo = shipmentInfoMapper.toEntity(shipmentInfoDTO);
		
		Vendor vendor = vendorRepository.findById(shipmentInfoDTO.getVendorId()).get();
		
		List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(shipmentInfoDTO.getConsignmentNo(), vendor);
		
		if(shipmentInfoList.size() > 0 && shipmentInfoDTO.getId() == null) {
			log.warn("Consignment No : " + shipmentInfoDTO.getConsignmentNo() + " - Already Exist in our System.");
			throw new Exception("Consignment Exist");
		}
		

		log.debug("Request to save ShiperInfo : {}", shipmentInfoDTO.getShipperInfo());
		ShiperReceiverInfo shiperInfo = shiperReceiverInfoMapper.toEntity(shipmentInfoDTO.getShipperInfo());
		shiperInfo.setType(ShiperReceiverType.CONSIGNOR);
		shiperInfo.setShipmentInfo(shipmentInfo);
		shiperInfo = shiperReceiverInfoRepository.save(shiperInfo);
		//shipmentInfo.addShipperReceiverInfos(shiperInfo);

		log.debug("Request to save ReceiverInfo : {}", shipmentInfoDTO.getReceiverInfo());
		ShiperReceiverInfo receiverInfo = shiperReceiverInfoMapper.toEntity(shipmentInfoDTO.getReceiverInfo());
		receiverInfo.setType(ShiperReceiverType.CONSIGNEE);
		receiverInfo.setShipmentInfo(shipmentInfo);
		receiverInfo = shiperReceiverInfoRepository.save(receiverInfo);
		//shipmentInfo.addShipperReceiverInfos(receiverInfo);

		ZoneId india = ZoneId.of("Asia/Kolkata");
		ShipmentTracking shipmentTracking = new ShipmentTracking();
		if (shipmentInfoDTO.getId() == null) {
			shipmentTracking.setPlace(stateRepository.findById(shipmentInfo.getOrigin().getId()).get().getStateName());
			shipmentTracking.setShipmentInfo(shipmentInfo);
			// shipmentTracking.setStatus(trackingStatusRepository.findById(shipmentInfo.getTrackingStatus().getId()).get().getValue());
			shipmentTracking.setStatus("Shipment Booked");
			shipmentTracking.setTrackingDate(ZonedDateTime.now(india));

			shipmentTrackingRepository.save(shipmentTracking);
			//shipmentInfo.addShipmentTrackings(shipmentTracking);
		}
		
		if(shipmentInfo.isIsThirdParty() == null)
			shipmentInfo.setIsThirdParty(false);
		shipmentInfo = shipmentInfoRepository.save(shipmentInfo);
		ShipmentInfoDTO result = shipmentInfoMapper.toDto(shipmentInfo);

		result.setCarrierDetailsValue(
				shipmentInfo.getCarrierDetails() != null ? shipmentInfo.getCarrierDetails().getValue() : null);
		result.setShipmentTypeValue(
				shipmentInfo.getShipmentType() != null ? shipmentInfo.getShipmentType().getValue() : null);
		result.setShipmentModeValue(
				shipmentInfo.getShipmentMode() != null ? shipmentInfo.getShipmentMode().getValue() : null);
		result.setPaymentModeValue(
				shipmentInfo.getPaymentMode() != null ? shipmentInfo.getPaymentMode().getValue() : null);
		result.setTrackingStatusValue(
				shipmentInfo.getTrackingStatus() != null ? shipmentInfo.getTrackingStatus().getValue() : null);
		result.setVendorname(shipmentInfo.getVendor() != null ? shipmentInfo.getVendor().getVendorname() : null);
		result.setOriginValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
		result.setDestinationValue(shipmentInfo.getOrigin() != null ? shipmentInfo.getOrigin().getStateName() : null);
		return result;
	}

	/**
	 * Get all the shipmentInfos.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ShipmentInfoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all ShipmentInfos");
		return shipmentInfoRepository.findAll(pageable).map(shipmentInfoMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ShipmentInfoDTO> findAllShipmentInformations(Pageable pageable,
			ShipmentInformationSearchDTO shipmentSearchDto) {
		log.debug("Request to get all ShipmentInformations");

		Page<ShipmentInfoDTO> shipmentInfoDTO = null;
		try {
			shipmentInfoDTO = shipmentInformationRepository.searchShipmentInfoQueryDSL(pageable, shipmentSearchDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Page<ShipmentInfoDTO> shipmentInformationsDTO =
		 * shipmentInfoRepository.findAll(pageable) .map(shipmentInfoMapper::toDto);
		 * 
		 * List<ShipmentInfoDTO> dTOs = shipmentInformationsDTO.getContent();
		 * 
		 * for(ShipmentInfoDTO dto : dTOs) { List<Long> ids = new ArrayList<Long>();
		 * ids.add(dto.getId()); List<ShiperReceiverInfo> shiperReceiverInfoList =
		 * shiperReceiverInfoRepository.findByIdIn(ids); //Optional<ShiperReceiverInfo>
		 * shiperReceiverInfoOpt = shiperReceiverInfoRepository.findById(dto.getId());
		 * //ShiperReceiverInfo shiperReceiverInfo = shiperReceiverInfoOpt.get();
		 * 
		 * ShiperReceiverInfoDTO shiperReceiverInfoDTO = null; for(ShiperReceiverInfo
		 * itr : shiperReceiverInfoList) { if(itr.getType() ==
		 * ShiperReceiverType.CONSIGNOR) { shiperReceiverInfoDTO = new
		 * ShiperReceiverInfoDTO(); BeanUtils.copyProperties(itr,
		 * shiperReceiverInfoDTO); shiperReceiverInfoDTO.setShipmentInfoId(dto.getId());
		 * dto.setShipperInfo(shiperReceiverInfoDTO); } else if(itr.getType() ==
		 * ShiperReceiverType.CONSIGNEE) { shiperReceiverInfoDTO = new
		 * ShiperReceiverInfoDTO(); BeanUtils.copyProperties(itr,
		 * shiperReceiverInfoDTO); shiperReceiverInfoDTO.setShipmentInfoId(dto.getId());
		 * dto.setReceiverInfo(shiperReceiverInfoDTO); } }
		 * 
		 * }
		 * 
		 * return new PageImpl<ShipmentInfoDTO>(dTOs,
		 * shipmentInformationsDTO.getPageable(),
		 * shipmentInformationsDTO.getTotalElements());
		 */
		return shipmentInfoDTO;
	}

	/**
	 * Get one shipmentInfo by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<ShipmentInfoDTO> findOne(Long id) {
		log.debug("Request to get ShipmentInfo : {}", id);
		return shipmentInfoRepository.findById(id).map(shipmentInfoMapper::toDto);
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
		while (shipperReceiverIterator.hasNext()) {
			ShiperReceiverInfo shiperReceiverInfo = shipperReceiverIterator.next();

			if (shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNOR) {
				shiperReceiverInfoDTO = new ShiperReceiverInfoDTO();
				BeanUtils.copyProperties(shiperReceiverInfo, shiperReceiverInfoDTO);
				shiperReceiverInfoDTO.setShipmentInfoId(shipmentInfoDTO.getId());
				shipmentInfoDTO.setShipperInfo(shiperReceiverInfoDTO);
			} else if (shiperReceiverInfo.getType() == ShiperReceiverType.CONSIGNEE) {
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
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete ShipmentInfo : {}", id);
		shipmentInfoRepository.deleteById(id);
		// shipmentInfoSearchRepository.deleteById(id);
	}

	@Override
	public ExcelResponse saveShipmentInformationBulk(MultipartFile file, Long vendorId) throws IOException {
		// TODO Auto-generated method stub
		ExcelResponse excelResponse = new ExcelResponse();
		List<String> errorList = new ArrayList<String>();
		POIFSFileSystem fs = null;
		XSSFWorkbook wb = null;
		try {

			//Shipment Type
			List<ShipmentTypeDTO> shipmentTypeDTOList = shipmentTypeService.findAllByVendor(vendorId);

			Map<String, Long> shipmentTypeMap = new HashMap<String, Long>();

			for (ShipmentTypeDTO itr : shipmentTypeDTOList) {
				shipmentTypeMap.put(itr.getValue(), itr.getId());
			}

			//ShipmentMode
			List<ShipmentModeDTO> shipmentModeDTOList = shipmentModeService.findAllByVendor(vendorId);

			Map<String, Long> shipmentModeMap = new HashMap<String, Long>();

			for (ShipmentModeDTO itr : shipmentModeDTOList) {
				shipmentModeMap.put(itr.getValue(), itr.getId());
			}

			//Payment Mode
			List<PaymentModeDTO> paymentModeDTOList = paymentModeService.findAllByVendor(vendorId);

			Map<String, Long> paymentModeMap = new HashMap<String, Long>();

			for (PaymentModeDTO itr : paymentModeDTOList) {
				paymentModeMap.put(itr.getValue(), itr.getId());
			}

			//Carrier Details
			List<CarrierDetailsDTO> carrierDetailsDTOList = carrierDetailsService.findAllByVendor(vendorId);

			Map<String, Long> carrierDetailsMap = new HashMap<String, Long>();

			for (CarrierDetailsDTO itr : carrierDetailsDTOList) {
				carrierDetailsMap.put(itr.getValue(), itr.getId());
			}

			//State -Source and Destination
			List<StateDTO> stateDTOList = stateService.findAll();

			Map<String, Long> stateMap = new HashMap<String, Long>();

			for (StateDTO itr : stateDTOList) {
				stateMap.put(itr.getStateName(), itr.getId());
			}
			
			//Map<String, Long> shipmentTypeMap
			Set<String> consignmentNo = new HashSet<String>();
			
			//Vendor Managed Model
			Vendor vendor = vendorRepository.findById(vendorId).get();
			
			//Work Book
			wb = new XSSFWorkbook(file.getInputStream());

			List<ShipmentInfoDTO> shipmentInfoDTOList = new ArrayList<ShipmentInfoDTO>();

			ShipmentInfoDTO shipmentInfoDTO = null;
			ShiperReceiverInfoDTO shipperInfoDTO = null;
			ShiperReceiverInfoDTO receiverInfoDTO = null;

			String pattern = "dd-MM-yyyy HH:mm";
			SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				if(i>0)
					break;
				XSSFSheet sheet = wb.getSheetAt(i);

				for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {
					shipmentInfoDTO = new ShipmentInfoDTO();
					shipperInfoDTO = new ShiperReceiverInfoDTO();
					receiverInfoDTO = new ShiperReceiverInfoDTO();
					XSSFRow row = sheet.getRow(j);
					XSSFRow rowHeader = sheet.getRow(0);

					for (int z = row.getFirstCellNum(); z <= row.getLastCellNum(); z++) {
						if(z > 31)
							break;
						Cell cell = row.getCell(z);
						if(cell != null)
							cell.setCellType(CellType.STRING);
						Cell cellHeader = rowHeader.getCell(z);
						if (z == 1) {
							// Consignment No
							
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								String consigmentNo = cell.getStringCellValue().trim();
								List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findByConsignmentNoAndVendor(consigmentNo, vendor);
								
								if(shipmentInfoList.size() > 0) {
									log.warn("Consignment No : " + consigmentNo + " - Already Exist in our System - Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue());
									errorList.add("Consignment No : " + consigmentNo + " - Already Exist in our System - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
									shipmentInfoDTO.setConsignmentNo(null);
									break;
								}
								else {
									if(consignmentNo.contains(consigmentNo)) {
										log.warn("Duplicate Consignment No : " + consigmentNo + " - Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
												+ cellHeader.getStringCellValue());
										errorList.add("Duplicate Consignment No : " + consigmentNo + " - Sheet - " + sheet.getSheetName() + " >> Row - " + j
												+ " >> Cell - " + cellHeader.getStringCellValue());
										shipmentInfoDTO.setConsignmentNo(null);
										break;
									}
									else {
										shipmentInfoDTO.setConsignmentNo(cell.getStringCellValue().trim());
										consignmentNo.add(cell.getStringCellValue().trim());
									}
									
								}
							}
								
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 2) {
							// Consignor Name
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setName(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 3) {
							// Consignor Phone No
							//cell.setCellType(CellType.STRING);
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setPhoneNo(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 4) {
							// Consignor Address
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setAddress(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 5) {
							// Consignor City
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setCity(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 6) {
							// Consignor Pin Code
							//cell.setCellType(CellType.STRING);
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setPincode(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 7) {
							// Consignor Email
							if (!HelperUtils.isExcelCellEmpty(cell))
								shipperInfoDTO.setEmailId(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 8) {
							// Consignee Name
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setName(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 9) {
							// Consignee Phone No
							//cell.setCellType(CellType.STRING);
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setPhoneNo(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 10) {
							// Consignee Address
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setAddress(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 11) {
							// Consignee City
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setCity(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 12) {
							// Consignee PinCode
							//cell.setCellType(CellType.STRING);
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setPincode(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 13) {
							// Consignee EmailId
							if (!HelperUtils.isExcelCellEmpty(cell))
								receiverInfoDTO.setEmailId(cell.getStringCellValue());
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 14) {
							// Booking Date
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								try {
									Date d;
									d = sDateFormat.parse(cell.getStringCellValue());

									shipmentInfoDTO.setBookingDate(
											ZonedDateTime.ofInstant(d.toInstant(), ZoneId.of("Asia/Kolkata")));
								} catch (Exception ex) {
									log.warn("Error - Date Format is not Correct. eg: 25-03-2018 12:30. Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}
							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 15) {
							// Expected Delivery Date
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								try {
									Date d;
									d = sDateFormat.parse(cell.getStringCellValue());

									shipmentInfoDTO.setExpectedDeliveryDate(
											ZonedDateTime.ofInstant(d.toInstant(), ZoneId.of("Asia/Kolkata")));
								} catch (Exception ex) {
									log.warn("Error - Date Format is not Correct. eg: 25-03-2018 12:30. Sheet - "
											+ sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							} else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 16) {
							// Shipment Type
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								if (shipmentTypeMap.containsKey(cell.getStringCellValue())) {
									shipmentInfoDTO.setShipmentTypeId(shipmentTypeMap.get(cell.getStringCellValue()));
									shipmentInfoDTO.setShipmentTypeValue(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue() + " >> "
											+ shipmentTypeMap.keySet().toArray());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}

							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 17) {
							// Shipment Mode
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								if (shipmentModeMap.containsKey(cell.getStringCellValue())) {
									shipmentInfoDTO.setShipmentModeId(shipmentModeMap.get(cell.getStringCellValue()));
									shipmentInfoDTO.setShipmentModeValue(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue() + " >> "
											+ shipmentModeMap.keySet().toArray());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}

							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 18) {
							// Payment Mode
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								if (paymentModeMap.containsKey(cell.getStringCellValue())) {
									shipmentInfoDTO.setPaymentModeId(paymentModeMap.get(cell.getStringCellValue()));
									shipmentInfoDTO.setPaymentModeValue(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue() + " >> "
											+ paymentModeMap.keySet().toArray());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}

							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 19) {
							// State Origin
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								if (stateMap.containsKey(cell.getStringCellValue())) {
									shipmentInfoDTO.setOriginId(stateMap.get(cell.getStringCellValue()));
									shipmentInfoDTO.setOriginValue(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue() + " >> "
											+ paymentModeMap.keySet().toArray());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}

							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 20) {
							// State Destination
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								if (stateMap.containsKey(cell.getStringCellValue())) {
									shipmentInfoDTO.setDestinationId(stateMap.get(cell.getStringCellValue()));
									shipmentInfoDTO.setDestinationValue(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue() + " >> "
											+ paymentModeMap.keySet().toArray());
									errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
											+ " >> Cell - " + cellHeader.getStringCellValue());
								}
							}

							else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 21) {
							// Actual Weight
							//cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setActualWeight(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Actual Weight is not Correct. (Eg : 76 or 56.78) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 22) {
							// Volumetric Weight
							//cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setVolumetricWeight(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Volumetric Weight is not Correct. (Eg : 76 or 56.78) Sheet - "
										+ sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
										+ cellHeader.getStringCellValue() + " >> " + paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 23) {
							// Length
							//cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setLength(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Length is not Correct. (Eg : 76 or 56.78) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 24) {
							// Width
							// cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setWidth(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Width is not Correct. (Eg : 76 or 56.78) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 25) {
							// Height
							// cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setHeight(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Height is not Correct. (Eg : 76 or 56.78) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 26) {
							// Quantity
							// cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setQuantity(Long.valueOf(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Quantity is not Correct. (Eg : 6 or 4) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 27) {
							// Total Fright
							// cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setTotalFright(new BigDecimal(cell.getStringCellValue()));
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("Total Fright is not Correct. (Eg : 76 or 56.78) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 28) {
							// Package Description
							if (!HelperUtils.isExcelCellEmpty(cell)) {
								shipmentInfoDTO.setPackageDesciption(cell.getStringCellValue());
							} else {
								log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
										+ cellHeader.getStringCellValue());
							}
						} else if (z == 29) {
							// Is Third Party
							// cell.setCellType(CellType.STRING);
							try {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setIsThirdParty(Boolean.valueOf(cell.getStringCellValue()));
								} else {
									shipmentInfoDTO.setIsThirdParty(false);
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " + j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}
							} catch (Exception ex) {
								log.warn("IsThirdParty is not Correct. (True or False) Sheet - " + sheet.getSheetName()
										+ " >> Row - " + j + " >> Cell - " + cellHeader.getStringCellValue() + " >> "
										+ paymentModeMap.keySet().toArray());
								errorList.add("Mandatory - Sheet - " + sheet.getSheetName() + " >> Row - " + j
										+ " >> Cell - " + cellHeader.getStringCellValue());
							}
						} else if (z == 30) {
							// Carrier No
							//cell.setCellType(CellType.STRING);
							if (shipmentInfoDTO.isIsThirdParty()) {

								if (!HelperUtils.isExcelCellEmpty(cell)) {
									shipmentInfoDTO.setCarrierRefNo(cell.getStringCellValue());
								} else {
									log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue());
									errorList.add(
											"Please enter Carrier Reference No or make (isThirdParty = No) - Sheet - "
													+ sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
													+ cellHeader.getStringCellValue());
								}
							}
						} else if (z == 31) {
							// Carrier Details
							//cell.setCellType(CellType.STRING);
							if (shipmentInfoDTO.isIsThirdParty()) {
								if (!HelperUtils.isExcelCellEmpty(cell)) {
									if (carrierDetailsMap.containsKey(cell.getStringCellValue())) {
										shipmentInfoDTO
												.setCarrierDetailsId(carrierDetailsMap.get(cell.getStringCellValue()));
										shipmentInfoDTO.setCarrierDetailsValue(cell.getStringCellValue());
									} else {
										log.warn("Sheet - " + sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
												+ cellHeader.getStringCellValue());
									}
								} else {
									errorList.add("Please select Carrier Details or make (isThirdParty = No) - Sheet - "
											+ sheet.getSheetName() + " >> Row - " +  j + " >> Cell - "
											+ cellHeader.getStringCellValue());
								}

							}
						}

					}
					if (!HelperUtils.isNullOrBlank(shipmentInfoDTO.getConsignmentNo())) {
						shipmentInfoDTO.setShipperInfo(shipperInfoDTO);
						shipmentInfoDTO.setReceiverInfo(receiverInfoDTO);
						shipmentInfoDTO.setVendorId(vendor.getId());
						shipmentInfoDTO.setVendorname(vendor.getVendorname());
						shipmentInfoDTOList.add(shipmentInfoDTO);
					}
				}

			}

			if (errorList.size() > 0) {
				return new ExcelResponse(true, errorList);
			}

			if (shipmentInfoDTOList.size() > 0) {
				for (ShipmentInfoDTO itr : shipmentInfoDTOList) {
					saveShipmentInformation(itr);
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

	/**
	 * Search for the shipmentInfo corresponding to the query.
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
	 * @Transactional(readOnly = true) public Page<ShipmentInfoDTO> search(String
	 * query, Pageable pageable) {
	 * log.debug("Request to search for a page of ShipmentInfos for query {}",
	 * query); return shipmentInfoSearchRepository.search(queryStringQuery(query),
	 * pageable) .map(shipmentInfoMapper::toDto); }
	 */
}
