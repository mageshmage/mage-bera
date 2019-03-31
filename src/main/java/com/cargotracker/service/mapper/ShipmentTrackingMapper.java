package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShipmentTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentTracking and its DTO ShipmentTrackingDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentInfoMapper.class})
public interface ShipmentTrackingMapper extends EntityMapper<ShipmentTrackingDTO, ShipmentTracking> {

    @Mapping(source = "shipmentInfo.id", target = "shipmentInfoId")
    ShipmentTrackingDTO toDto(ShipmentTracking shipmentTracking);

    @Mapping(source = "shipmentInfoId", target = "shipmentInfo")
    ShipmentTracking toEntity(ShipmentTrackingDTO shipmentTrackingDTO);

    default ShipmentTracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentTracking shipmentTracking = new ShipmentTracking();
        shipmentTracking.setId(id);
        return shipmentTracking;
    }
}
