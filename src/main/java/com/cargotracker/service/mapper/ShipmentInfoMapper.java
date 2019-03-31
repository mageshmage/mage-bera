package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.ShipmentInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShipmentInfo and its DTO ShipmentInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CarrierDetailsMapper.class, ShipmentTypeMapper.class, ShipmentModeMapper.class, PaymentModeMapper.class, TrackingStatusMapper.class, VendorMapper.class, StateMapper.class})
public interface ShipmentInfoMapper extends EntityMapper<ShipmentInfoDTO, ShipmentInfo> {

    @Mapping(source = "carrierDetails.id", target = "carrierDetailsId")
    @Mapping(source = "shipmentType.id", target = "shipmentTypeId")
    @Mapping(source = "shipmentMode.id", target = "shipmentModeId")
    @Mapping(source = "paymentMode.id", target = "paymentModeId")
    @Mapping(source = "trackingStatus.id", target = "trackingStatusId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "origin.id", target = "originId")
    @Mapping(source = "destination.id", target = "destinationId")
    ShipmentInfoDTO toDto(ShipmentInfo shipmentInfo);

    @Mapping(source = "carrierDetailsId", target = "carrierDetails")
    @Mapping(source = "shipmentTypeId", target = "shipmentType")
    @Mapping(source = "shipmentModeId", target = "shipmentMode")
    @Mapping(source = "paymentModeId", target = "paymentMode")
    @Mapping(source = "trackingStatusId", target = "trackingStatus")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "originId", target = "origin")
    @Mapping(source = "destinationId", target = "destination")
    @Mapping(target = "shipmentTrackings", ignore = true)
    @Mapping(target = "shipmentInfoPODs", ignore = true)
    @Mapping(target = "shipperReceiverInfos", ignore = true)
    ShipmentInfo toEntity(ShipmentInfoDTO shipmentInfoDTO);

    default ShipmentInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShipmentInfo shipmentInfo = new ShipmentInfo();
        shipmentInfo.setId(id);
        return shipmentInfo;
    }
}
