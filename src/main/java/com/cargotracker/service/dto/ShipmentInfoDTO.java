package com.cargotracker.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ShipmentInfo entity.
 */
public class ShipmentInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String consignmentNo;

    @NotNull
    private ZonedDateTime bookingDate;

    @NotNull
    private ZonedDateTime expectedDeliveryDate;

    private BigDecimal actualWeight;

    private BigDecimal volumetricWeight;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private Long quantity;

    private BigDecimal totalFright;

    private String packageDesciption;

    @NotNull
    private Boolean isThirdParty;

    private String carrierRefNo;

    private ZonedDateTime deliveredDate;

    private String receivedBy;

    private String relationShip;


    private Long carrierDetailsId;

    private Long shipmentTypeId;

    private Long shipmentModeId;

    private Long paymentModeId;

    private Long trackingStatusId;

    private Long vendorId;

    private Long originId;

    private Long destinationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public ZonedDateTime getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(ZonedDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    public BigDecimal getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(BigDecimal volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalFright() {
        return totalFright;
    }

    public void setTotalFright(BigDecimal totalFright) {
        this.totalFright = totalFright;
    }

    public String getPackageDesciption() {
        return packageDesciption;
    }

    public void setPackageDesciption(String packageDesciption) {
        this.packageDesciption = packageDesciption;
    }

    public Boolean isIsThirdParty() {
        return isThirdParty;
    }

    public void setIsThirdParty(Boolean isThirdParty) {
        this.isThirdParty = isThirdParty;
    }

    public String getCarrierRefNo() {
        return carrierRefNo;
    }

    public void setCarrierRefNo(String carrierRefNo) {
        this.carrierRefNo = carrierRefNo;
    }

    public ZonedDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(ZonedDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
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

    public void setOriginId(Long stateId) {
        this.originId = stateId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long stateId) {
        this.destinationId = stateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentInfoDTO shipmentInfoDTO = (ShipmentInfoDTO) o;
        if (shipmentInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentInfoDTO{" +
            "id=" + getId() +
            ", consignmentNo='" + getConsignmentNo() + "'" +
            ", bookingDate='" + getBookingDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", actualWeight=" + getActualWeight() +
            ", volumetricWeight=" + getVolumetricWeight() +
            ", length=" + getLength() +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", quantity=" + getQuantity() +
            ", totalFright=" + getTotalFright() +
            ", packageDesciption='" + getPackageDesciption() + "'" +
            ", isThirdParty='" + isIsThirdParty() + "'" +
            ", carrierRefNo='" + getCarrierRefNo() + "'" +
            ", deliveredDate='" + getDeliveredDate() + "'" +
            ", receivedBy='" + getReceivedBy() + "'" +
            ", relationShip='" + getRelationShip() + "'" +
            ", carrierDetails=" + getCarrierDetailsId() +
            ", shipmentType=" + getShipmentTypeId() +
            ", shipmentMode=" + getShipmentModeId() +
            ", paymentMode=" + getPaymentModeId() +
            ", trackingStatus=" + getTrackingStatusId() +
            ", vendor=" + getVendorId() +
            ", origin=" + getOriginId() +
            ", destination=" + getDestinationId() +
            "}";
    }
}
