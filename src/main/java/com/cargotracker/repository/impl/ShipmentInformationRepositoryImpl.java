package com.cargotracker.repository.impl;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.cargotracker.domain.QShiperReceiverInfo;
import com.cargotracker.domain.QShipmentInfo;
import com.cargotracker.domain.SeriesTable;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.enumeration.ShiperReceiverType;
import com.cargotracker.repository.CustomShipmentInformationRepository;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;
import com.cargotracker.service.mapper.ShipmentInfoMapper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

public class ShipmentInformationRepositoryImpl implements CustomShipmentInformationRepository {

	private final Logger log = LoggerFactory.getLogger(ShipmentInformationRepositoryImpl.class);
	
	@Autowired
	private ShiperReceiverInfoRepository shiperReceiverInfoRepository;
	
	@Autowired
	private ShipmentInfoRepository shipmentInfoRepository;
	
	@Autowired
	private ShipmentInfoMapper shipmentInfoMapper;

	@PersistenceContext
	private EntityManager entityManager;
	
	public String getSeries( String seriesName ) {
		
		StringBuilder sql = new StringBuilder();
		sql.append( "SELECT * FROM series_table WHERE name = '" )
		   .append( seriesName ).append( "' FOR UPDATE"); 
		Query query = entityManager.createNativeQuery( sql.toString(), SeriesTable.class );
		SeriesTable series = (SeriesTable) query.getSingleResult();
		String value = null;
		if ( series.getPrefix() != null ) {
			value = series.getPrefix();
		}
		value += series.getNextSeries();
		series.setNextSeries(  series.getNextSeries() + 1 );		
		
		return value;
	}

	public Page<ShipmentInfoDTO> searchShipmentInfoQueryDSL(Pageable pageable,
			ShipmentInformationSearchDTO shipmentSearchDto) throws Exception {
		List<ShipmentInfoDTO> shipmentInfoDTOList = null;
		long total = 0;
		try {
			shipmentInfoDTOList = new ArrayList<ShipmentInfoDTO>();
			QShipmentInfo qShipmentInfo = QShipmentInfo.shipmentInfo;
			QShiperReceiverInfo qShiperReceiverInfo = QShiperReceiverInfo.shiperReceiverInfo;
			//QSupplierUser qSupplierUser = QSupplierUser.supplierUser;

			JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);

			query.select(qShipmentInfo.id, qShipmentInfo.consignmentNo
					//, qShipmentInfo.consignmentNo, qShipmentInfo.bookingDate, qShipmentInfo.expectedDeliveryDate,
					// qShipmentInfo.actualWeight, qShipmentInfo.shipmentType.id, qShipmentInfo.shipmentType.value,
					
					//qShipmentInfo.carrierDetails.id, qShipmentInfo.carrierDetails.value
					/*, qShipmentInfo.volumetricWeight, qShipmentInfo.length, qShipmentInfo.width, qShipmentInfo.height,
					qShipmentInfo.quantity, qShipmentInfo.totalFright, qShipmentInfo.packageDesciption,
					qShipmentInfo.isThirdParty, qShipmentInfo.carrierRefNo, qShipmentInfo.deliveredDate,
					qShipmentInfo.receivedBy, qShipmentInfo.relationShip, 
					qShipmentInfo.shipmentMode.id, qShipmentInfo.shipmentMode.value,
					qShipmentInfo.shipmentMode.id, qShipmentInfo.shipmentMode.value, qShipmentInfo.trackingStatus.id, qShipmentInfo.trackingStatus.value,
					qShipmentInfo.vendor.id, qShipmentInfo.vendor.vendorname, qShipmentInfo.origin.id, qShipmentInfo.origin.stateName,
					qShipmentInfo.destination.id, qShipmentInfo.destination.stateName*/
					).from(qShipmentInfo);
				
			if (shipmentSearchDto.getVendorId() != null) {
				query.where((qShipmentInfo.vendor.id.eq(shipmentSearchDto.getVendorId())));
			}
			else {
				return new PageImpl<ShipmentInfoDTO>(shipmentInfoDTOList, pageable, total);
			}
			
			if (shipmentSearchDto.getTrackingStatusId() != null && shipmentSearchDto.getTrackingStatusId() > 0) {
				query.where((qShipmentInfo.trackingStatus.id.eq(shipmentSearchDto.getTrackingStatusId())));
			} else {
				//query.where((qSupplierGeneralInfo.status.notIn(SupplierStatus.DRAFT)));
			}
			if (shipmentSearchDto.getConsignmentNo() != null && shipmentSearchDto.getConsignmentNo().trim().length() > 0) {
				query.where((qShipmentInfo.consignmentNo.contains(shipmentSearchDto.getConsignmentNo().trim())));
			}
			if (shipmentSearchDto.getBookingDateFrom() != null) {
				if(shipmentSearchDto.getBookingDateTo() == null) {
					shipmentSearchDto.setBookingDateTo(ZonedDateTime.now());
				}
				query.where((qShipmentInfo.bookingDate.between(shipmentSearchDto.getBookingDateFrom(), shipmentSearchDto.getBookingDateTo())));
			}
			else {
				query.where((qShipmentInfo.bookingDate.between(ZonedDateTime.now().minusDays(1), ZonedDateTime.now().plusDays(1))));
			}
			
			if (shipmentSearchDto.getDeliveredDateFrom() != null) {
				if(shipmentSearchDto.getDeliveredDateTo() == null) {
					shipmentSearchDto.setDeliveredDateTo(ZonedDateTime.now());
				}
				query.where((qShipmentInfo.deliveredDate.between(shipmentSearchDto.getDeliveredDateFrom(), shipmentSearchDto.getDeliveredDateTo())));
			}
			if (shipmentSearchDto.getExpectedDeliveryDateFrom() != null) {
				if(shipmentSearchDto.getExpectedDeliveryDateTo() == null) {
					shipmentSearchDto.setExpectedDeliveryDateTo(ZonedDateTime.now());
				}
				query.where((qShipmentInfo.expectedDeliveryDate.between(shipmentSearchDto.getExpectedDeliveryDateFrom(), shipmentSearchDto.getExpectedDeliveryDateTo())));
			}
			
			if (shipmentSearchDto.getCarrierRefNo() != null && shipmentSearchDto.getCarrierRefNo().trim().length() > 0) {
				query.where((qShipmentInfo.carrierRefNo.contains(shipmentSearchDto.getCarrierRefNo().trim())));
			}
			if (shipmentSearchDto.getCarrierDetailsId() != null && shipmentSearchDto.getCarrierDetailsId() > 0) {
				query.where((qShipmentInfo.carrierDetails.id.eq(shipmentSearchDto.getCarrierDetailsId())));
			}
			if (shipmentSearchDto.getShipmentTypeId() != null && shipmentSearchDto.getShipmentTypeId() > 0) {
				query.where((qShipmentInfo.shipmentType.id.eq(shipmentSearchDto.getShipmentTypeId())));
			}
			if (shipmentSearchDto.getShipmentModeId() != null && shipmentSearchDto.getShipmentModeId() > 0) {
				query.where((qShipmentInfo.shipmentMode.id.eq(shipmentSearchDto.getShipmentModeId())));
			}
			if (shipmentSearchDto.getPaymentModeId() != null && shipmentSearchDto.getPaymentModeId() > 0) {
				query.where((qShipmentInfo.paymentMode.id.eq(shipmentSearchDto.getPaymentModeId())));
			}
			
			if (shipmentSearchDto.getOriginId() != null && shipmentSearchDto.getOriginId() > 0) {
				query.where((qShipmentInfo.origin.id.eq(shipmentSearchDto.getOriginId())));
			}
			
			if (shipmentSearchDto.getDestinationId() != null && shipmentSearchDto.getDestinationId() > 0) {
				query.where((qShipmentInfo.destination.id.eq(shipmentSearchDto.getDestinationId())));
			}
			

			query.orderBy(qShipmentInfo.bookingDate.desc());
			query.offset(pageable.getPageNumber()*pageable.getPageSize());
			query.limit(pageable.getPageSize());
			

			total = query.fetchCount();
			List<Tuple> content = query.fetch();

			for (Tuple tuple : content) {
				//ShipmentInfoDTO shipmentInfoDTO = new ShipmentInfoDTO();
				
				Long id = tuple.get(qShipmentInfo.id).longValue();
				
				Optional<ShipmentInfo> shipmentInfo = shipmentInfoRepository.findById(id);
				
				if(shipmentInfo.isPresent()) {
				ShipmentInfoDTO shipmentInfoDTO = shipmentInfo.map(shipmentInfoMapper::toDto).get();
				
				//List<Long> ids = new ArrayList<Long>();
	        	//ids.add(shipmentInfo.getId());
				
				//List<ShiperReceiverInfo> shiperReceiverInfoList =  shiperReceiverInfoRepository.findByShipmentInfoIn(ids);
	        	
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
				
				
				/*SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				Date orgDate = tuple.get(qSupplierGeneralInfo.submissionTime);
				String date;
				if(orgDate != null) {
					date = dateFormat.format(orgDate);
					supplierSearchResponseDTO.setSubmissionTime(date);
				}*/
				shipmentInfoDTOList.add(shipmentInfoDTO);
				}
			}

		} catch (Exception ex) {
			log.error("Exception occured - " + ex.getMessage());
			throw new Exception(ex.getMessage());
		}

		return new PageImpl<ShipmentInfoDTO>(shipmentInfoDTOList, pageable, total);
	}

}
