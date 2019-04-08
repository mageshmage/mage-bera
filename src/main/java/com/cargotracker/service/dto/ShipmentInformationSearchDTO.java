package com.cargotracker.service.dto;

import java.time.ZonedDateTime;

public class ShipmentInformationSearchDTO {

	private String consignmentNo;

	private ZonedDateTime bookingDateFrom;

	private ZonedDateTime bookingDateTo;

	private ZonedDateTime expectedDeliveryDateFrom;

	private ZonedDateTime expectedDeliveryDateTo;

	private String carrierRefNo;

	private ZonedDateTime deliveredDateFrom;

	private ZonedDateTime deliveredDateTo;

	private Long carrierDetailsId;

	private Long shipmentTypeId;

	private Long shipmentModeId;

	private Long paymentModeId;

	private Long trackingStatusId;

	private Long vendorId;

	private Long originId;

	private Long destinationId;

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public ZonedDateTime getBookingDateFrom() {
		return bookingDateFrom;
	}

	public void setBookingDateFrom(ZonedDateTime bookingDateFrom) {
		this.bookingDateFrom = bookingDateFrom;
	}

	public ZonedDateTime getBookingDateTo() {
		return bookingDateTo;
	}

	public void setBookingDateTo(ZonedDateTime bookingDateTo) {
		this.bookingDateTo = bookingDateTo;
	}

	public ZonedDateTime getExpectedDeliveryDateFrom() {
		return expectedDeliveryDateFrom;
	}

	public void setExpectedDeliveryDateFrom(ZonedDateTime expectedDeliveryDateFrom) {
		this.expectedDeliveryDateFrom = expectedDeliveryDateFrom;
	}

	public ZonedDateTime getExpectedDeliveryDateTo() {
		return expectedDeliveryDateTo;
	}

	public void setExpectedDeliveryDateTo(ZonedDateTime expectedDeliveryDateTo) {
		this.expectedDeliveryDateTo = expectedDeliveryDateTo;
	}

	public String getCarrierRefNo() {
		return carrierRefNo;
	}

	public void setCarrierRefNo(String carrierRefNo) {
		this.carrierRefNo = carrierRefNo;
	}

	public ZonedDateTime getDeliveredDateFrom() {
		return deliveredDateFrom;
	}

	public void setDeliveredDateFrom(ZonedDateTime deliveredDateFrom) {
		this.deliveredDateFrom = deliveredDateFrom;
	}

	public ZonedDateTime getDeliveredDateTo() {
		return deliveredDateTo;
	}

	public void setDeliveredDateTo(ZonedDateTime deliveredDateTo) {
		this.deliveredDateTo = deliveredDateTo;
	}

	public Long getCarrierDetailsId() {
		return carrierDetailsId;
	}

	public void setCarrierDetailsId(Long carrierDetailsId) {
		this.carrierDetailsId = carrierDetailsId;
	}

	public Long getShipmentTypeId() {
		return shipmentTypeId;
	}

	public void setShipmentTypeId(Long shipmentTypeId) {
		this.shipmentTypeId = shipmentTypeId;
	}

	public Long getShipmentModeId() {
		return shipmentModeId;
	}

	public void setShipmentModeId(Long shipmentModeId) {
		this.shipmentModeId = shipmentModeId;
	}

	public Long getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(Long paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public Long getTrackingStatusId() {
		return trackingStatusId;
	}

	public void setTrackingStatusId(Long trackingStatusId) {
		this.trackingStatusId = trackingStatusId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

}
