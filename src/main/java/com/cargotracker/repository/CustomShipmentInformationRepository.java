package com.cargotracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;

public interface CustomShipmentInformationRepository {
	
	public String getSeries( String seriesName );

	public Page<ShipmentInfoDTO> searchShipmentInfoQueryDSL(Pageable pageable,
			ShipmentInformationSearchDTO supplierSearchDto) throws Exception;

}
