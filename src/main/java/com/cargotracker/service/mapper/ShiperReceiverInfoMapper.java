package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShiperReceiverInfo and its DTO ShiperReceiverInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {ShipmentInfoMapper.class})
public interface ShiperReceiverInfoMapper extends EntityMapper<ShiperReceiverInfoDTO, ShiperReceiverInfo> {

    @Mapping(source = "shipmentInfo.id", target = "shipmentInfoId")
    ShiperReceiverInfoDTO toDto(ShiperReceiverInfo shiperReceiverInfo);

    @Mapping(source = "shipmentInfoId", target = "shipmentInfo")
    ShiperReceiverInfo toEntity(ShiperReceiverInfoDTO shiperReceiverInfoDTO);

    default ShiperReceiverInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShiperReceiverInfo shiperReceiverInfo = new ShiperReceiverInfo();
        shiperReceiverInfo.setId(id);
        return shiperReceiverInfo;
    }
}
