package com.cargotracker.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ShipmentTracking entity.
 */
public class ShipmentTrackingDTO implements Serializable {

	private Long id;

	@NotNull
	private ZonedDateTime trackingDate;

	@NotNull
	private String place;

	@NotNull
	private String status;

	private Long shipmentInfoId;

	private Boolean isDelivered;

	private Boolean isInTransit;

	private Boolean isReachedNearestHub;

	private Boolean isOutForDelivery;

	private String receivedBy;

	private String relationShip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getTrackingDate() {
		return trackingDate;
	}

	public void setTrackingDate(ZonedDateTime trackingDate) {
		this.trackingDate = trackingDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getShipmentInfoId() {
		return shipmentInfoId;
	}

	public void setShipmentInfoId(Long shipmentInfoId) {
		this.shipmentInfoId = shipmentInfoId;
	}

	public Boolean getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(Boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public Boolean getIsInTransit() {
		return isInTransit;
	}

	public void setIsInTransit(Boolean isInTransit) {
		this.isInTransit = isInTransit;
	}

	public Boolean getIsReachedNearestHub() {
		return isReachedNearestHub;
	}

	public void setIsReachedNearestHub(Boolean isReachedNearestHub) {
		this.isReachedNearestHub = isReachedNearestHub;
	}

	public Boolean getIsOutForDelivery() {
		return isOutForDelivery;
	}

	public void setIsOutForDelivery(Boolean isOutForDelivery) {
		this.isOutForDelivery = isOutForDelivery;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ShipmentTrackingDTO shipmentTrackingDTO = (ShipmentTrackingDTO) o;
		if (shipmentTrackingDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), shipmentTrackingDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ShipmentTrackingDTO{" + "id=" + getId() + ", trackingDate='" + getTrackingDate() + "'" + ", place='"
				+ getPlace() + "'" + ", status='" + getStatus() + "'" + ", shipmentInfo=" + getShipmentInfoId() + "}";
	}
}
