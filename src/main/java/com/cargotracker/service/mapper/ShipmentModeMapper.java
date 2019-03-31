package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShipmentModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentMode and its DTO ShipmentModeDTO.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class })
public interface ShipmentModeMapper extends EntityMapper<ShipmentModeDTO, ShipmentMode> {

	@Mappings({ @Mapping(source = "vendor.id", target = "vendorId"),
			@Mapping(source = "vendor.vendorname", target = "vendorname") })
	ShipmentModeDTO toDto(ShipmentMode shipmentMode);

	@Mapping(source = "vendorId", target = "vendor")
	ShipmentMode toEntity(ShipmentModeDTO shipmentModeDTO);

	default ShipmentMode fromId(Long id) {
		if (id == null) {
			return null;
		}
		ShipmentMode shipmentMode = new ShipmentMode();
		shipmentMode.setId(id);
		return shipmentMode;
	}
}
