package com.cargotracker.service.mapper;

import com.cargotracker.domain.*;
import com.cargotracker.service.dto.PaymentModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentMode and its DTO PaymentModeDTO.
 */
@Mapper(componentModel = "spring", uses = { VendorMapper.class })
public interface PaymentModeMapper extends EntityMapper<PaymentModeDTO, PaymentMode> {

	@Mappings({ @Mapping(source = "vendor.id", target = "vendorId"),
			@Mapping(source = "vendor.vendorname", target = "vendorname") })
	PaymentModeDTO toDto(PaymentMode paymentMode);

	@Mapping(source = "vendorId", target = "vendor")
	PaymentMode toEntity(PaymentModeDTO paymentModeDTO);

	default PaymentMode fromId(Long id) {
		if (id == null) {
			return null;
		}
		PaymentMode paymentMode = new PaymentMode();
		paymentMode.setId(id);
		return paymentMode;
	}
}
