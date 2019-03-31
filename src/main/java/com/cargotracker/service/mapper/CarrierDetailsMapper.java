package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.CarrierDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarrierDetails and its DTO CarrierDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class })
public interface CarrierDetailsMapper extends EntityMapper<CarrierDetailsDTO, CarrierDetails> {

	@Mappings({ @Mapping(source = "vendor.id", target = "vendorId"),
			@Mapping(source = "vendor.vendorname", target = "vendorname") })
	CarrierDetailsDTO toDto(CarrierDetails carrierDetails);

	@Mapping(source = "vendorId", target = "vendor")
	CarrierDetails toEntity(CarrierDetailsDTO carrierDetailsDTO);

	default CarrierDetails fromId(Long id) {
		if (id == null) {
			return null;
		}
		CarrierDetails carrierDetails = new CarrierDetails();
		carrierDetails.setId(id);
		return carrierDetails;
	}
}
