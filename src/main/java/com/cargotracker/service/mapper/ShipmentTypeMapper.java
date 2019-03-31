package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShipmentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentType and its DTO ShipmentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {VendorMapper.class})
public interface ShipmentTypeMapper extends EntityMapper<ShipmentTypeDTO, ShipmentType> {

    @Mapping(source = "vendor.id", target = "vendorId")
    ShipmentTypeDTO toDto(ShipmentType shipmentType);

    @Mapping(source = "vendorId", target = "vendor")
    ShipmentType toEntity(ShipmentTypeDTO shipmentTypeDTO);

    default ShipmentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentType shipmentType = new ShipmentType();
        shipmentType.setId(id);
        return shipmentType;
    }
}
