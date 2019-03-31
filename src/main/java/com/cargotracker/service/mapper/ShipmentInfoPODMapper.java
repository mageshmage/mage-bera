package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentInfoPOD and its DTO ShipmentInfoPODDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentInfoMapper.class})
public interface ShipmentInfoPODMapper extends EntityMapper<ShipmentInfoPODDTO, ShipmentInfoPOD> {

    @Mapping(source = "shipmentInfo.id", target = "shipmentInfoId")
    ShipmentInfoPODDTO toDto(ShipmentInfoPOD shipmentInfoPOD);

    @Mapping(source = "shipmentInfoId", target = "shipmentInfo")
    ShipmentInfoPOD toEntity(ShipmentInfoPODDTO shipmentInfoPODDTO);

    default ShipmentInfoPOD fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentInfoPOD shipmentInfoPOD = new ShipmentInfoPOD();
        shipmentInfoPOD.setId(id);
        return shipmentInfoPOD;
    }
}
