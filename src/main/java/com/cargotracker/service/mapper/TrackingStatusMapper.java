package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.TrackingStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrackingStatus and its DTO TrackingStatusDTO.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class })
public interface TrackingStatusMapper extends EntityMapper<TrackingStatusDTO, TrackingStatus> {

	@Mappings({ @Mapping(source = "vendor.id", target = "vendorId"),
			@Mapping(source = "vendor.vendorname", target = "vendorname") })
	TrackingStatusDTO toDto(TrackingStatus trackingStatus);

	@Mapping(source = "vendorId", target = "vendor")
	TrackingStatus toEntity(TrackingStatusDTO trackingStatusDTO);

	default TrackingStatus fromId(Long id) {
		if (id == null) {
			return null;
		}
		TrackingStatus trackingStatus = new TrackingStatus();
		trackingStatus.setId(id);
		return trackingStatus;
	}
}
