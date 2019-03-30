package com.cargotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ShipmentInfo.
 */
@Entity
@Table(name = "shipment_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shipmentinfo")
public class ShipmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "consignment_no", nullable = false)
    private String consignmentNo;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private ZonedDateTime bookingDate;

    @NotNull
    @Column(name = "expected_delivery_date", nullable = false)
    private ZonedDateTime expectedDeliveryDate;

    @Column(name = "actual_weight", precision = 10, scale = 2)
    private BigDecimal actualWeight;

    @Column(name = "volumetric_weight", precision = 10, scale = 2)
    private BigDecimal volumetricWeight;

    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length;

    @Column(name = "width", precision = 10, scale = 2)
    private BigDecimal width;

    @Column(name = "height", precision = 10, scale = 2)
    private BigDecimal height;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_fright", precision = 10, scale = 2)
    private BigDecimal totalFright;

    @Column(name = "package_desciption")
    private String packageDesciption;

    @NotNull
    @Column(name = "is_third_party", nullable = false)
    private Boolean isThirdParty;

    @Column(name = "carrier_ref_no")
    private String carrierRefNo;

    @Column(name = "delivered_date")
    private ZonedDateTime deliveredDate;

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "relation_ship")
    private String relationShip;

    @OneToOne
    @JoinColumn(unique = true)
    private CarrierDetails carrierDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private ShipmentType shipmentType;

    @OneToOne
    @JoinColumn(unique = true)
    private ShipmentMode shipmentMode;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentMode paymentMode;

    @OneToOne
    @JoinColumn(unique = true)
    private TrackingStatus trackingStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private Vendor vendor;

    @OneToOne
    @JoinColumn(unique = true)
    private State origin;

    @OneToOne
    @JoinColumn(unique = true)
    private State destination;

    @OneToMany(mappedBy = "shipmentInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShipmentTracking> shipmentTrackings = new HashSet<>();
    @OneToMany(mappedBy = "shipmentInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShipmentInfoPOD> shipmentInfoPODs = new HashSet<>();
    @OneToMany(mappedBy = "shipmentInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShiperReceiverInfo> shipperReceiverInfos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public ShipmentInfo consignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
        return this;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public ShipmentInfo bookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public ZonedDateTime getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public ShipmentInfo expectedDeliveryDate(ZonedDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(ZonedDateTime expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public ShipmentInfo actualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
        return this;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    public BigDecimal getVolumetricWeight() {
        return volumetricWeight;
    }

    public ShipmentInfo volumetricWeight(BigDecimal volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
        return this;
    }

    public void setVolumetricWeight(BigDecimal volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public ShipmentInfo length(BigDecimal length) {
        this.length = length;
        return this;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public ShipmentInfo width(BigDecimal width) {
        this.width = width;
        return this;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public ShipmentInfo height(BigDecimal height) {
        this.height = height;
        return this;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ShipmentInfo quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalFright() {
        return totalFright;
    }

    public ShipmentInfo totalFright(BigDecimal totalFright) {
        this.totalFright = totalFright;
        return this;
    }

    public void setTotalFright(BigDecimal totalFright) {
        this.totalFright = totalFright;
    }

    public String getPackageDesciption() {
        return packageDesciption;
    }

    public ShipmentInfo packageDesciption(String packageDesciption) {
        this.packageDesciption = packageDesciption;
        return this;
    }

    public void setPackageDesciption(String packageDesciption) {
        this.packageDesciption = packageDesciption;
    }

    public Boolean isIsThirdParty() {
        return isThirdParty;
    }

    public ShipmentInfo isThirdParty(Boolean isThirdParty) {
        this.isThirdParty = isThirdParty;
        return this;
    }

    public void setIsThirdParty(Boolean isThirdParty) {
        this.isThirdParty = isThirdParty;
    }

    public String getCarrierRefNo() {
        return carrierRefNo;
    }

    public ShipmentInfo carrierRefNo(String carrierRefNo) {
        this.carrierRefNo = carrierRefNo;
        return this;
    }

    public void setCarrierRefNo(String carrierRefNo) {
        this.carrierRefNo = carrierRefNo;
    }

    public ZonedDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public ShipmentInfo deliveredDate(ZonedDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
        return this;
    }

    public void setDeliveredDate(ZonedDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public ShipmentInfo receivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
        return this;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public ShipmentInfo relationShip(String relationShip) {
        this.relationShip = relationShip;
        return this;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public CarrierDetails getCarrierDetails() {
        return carrierDetails;
    }

    public ShipmentInfo carrierDetails(CarrierDetails carrierDetails) {
        this.carrierDetails = carrierDetails;
        return this;
    }

    public void setCarrierDetails(CarrierDetails carrierDetails) {
        this.carrierDetails = carrierDetails;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public ShipmentInfo shipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
        return this;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public ShipmentMode getShipmentMode() {
        return shipmentMode;
    }

    public ShipmentInfo shipmentMode(ShipmentMode shipmentMode) {
        this.shipmentMode = shipmentMode;
        return this;
    }

    public void setShipmentMode(ShipmentMode shipmentMode) {
        this.shipmentMode = shipmentMode;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public ShipmentInfo paymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public TrackingStatus getTrackingStatus() {
        return trackingStatus;
    }

    public ShipmentInfo trackingStatus(TrackingStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
        return this;
    }

    public void setTrackingStatus(TrackingStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public ShipmentInfo vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public State getOrigin() {
        return origin;
    }

    public ShipmentInfo origin(State state) {
        this.origin = state;
        return this;
    }

    public void setOrigin(State state) {
        this.origin = state;
    }

    public State getDestination() {
        return destination;
    }

    public ShipmentInfo destination(State state) {
        this.destination = state;
        return this;
    }

    public void setDestination(State state) {
        this.destination = state;
    }

    public Set<ShipmentTracking> getShipmentTrackings() {
        return shipmentTrackings;
    }

    public ShipmentInfo shipmentTrackings(Set<ShipmentTracking> shipmentTrackings) {
        this.shipmentTrackings = shipmentTrackings;
        return this;
    }

    public ShipmentInfo addShipmentTrackings(ShipmentTracking shipmentTracking) {
        this.shipmentTrackings.add(shipmentTracking);
        shipmentTracking.setShipmentInfo(this);
        return this;
    }

    public ShipmentInfo removeShipmentTrackings(ShipmentTracking shipmentTracking) {
        this.shipmentTrackings.remove(shipmentTracking);
        shipmentTracking.setShipmentInfo(null);
        return this;
    }

    public void setShipmentTrackings(Set<ShipmentTracking> shipmentTrackings) {
        this.shipmentTrackings = shipmentTrackings;
    }

    public Set<ShipmentInfoPOD> getShipmentInfoPODs() {
        return shipmentInfoPODs;
    }

    public ShipmentInfo shipmentInfoPODs(Set<ShipmentInfoPOD> shipmentInfoPODS) {
        this.shipmentInfoPODs = shipmentInfoPODS;
        return this;
    }

    public ShipmentInfo addShipmentInfoPODs(ShipmentInfoPOD shipmentInfoPOD) {
        this.shipmentInfoPODs.add(shipmentInfoPOD);
        shipmentInfoPOD.setShipmentInfo(this);
        return this;
    }

    public ShipmentInfo removeShipmentInfoPODs(ShipmentInfoPOD shipmentInfoPOD) {
        this.shipmentInfoPODs.remove(shipmentInfoPOD);
        shipmentInfoPOD.setShipmentInfo(null);
        return this;
    }

    public void setShipmentInfoPODs(Set<ShipmentInfoPOD> shipmentInfoPODS) {
        this.shipmentInfoPODs = shipmentInfoPODS;
    }

    public Set<ShiperReceiverInfo> getShipperReceiverInfos() {
        return shipperReceiverInfos;
    }

    public ShipmentInfo shipperReceiverInfos(Set<ShiperReceiverInfo> shiperReceiverInfos) {
        this.shipperReceiverInfos = shiperReceiverInfos;
        return this;
    }

    public ShipmentInfo addShipperReceiverInfos(ShiperReceiverInfo shiperReceiverInfo) {
        this.shipperReceiverInfos.add(shiperReceiverInfo);
        shiperReceiverInfo.setShipmentInfo(this);
        return this;
    }

    public ShipmentInfo removeShipperReceiverInfos(ShiperReceiverInfo shiperReceiverInfo) {
        this.shipperReceiverInfos.remove(shiperReceiverInfo);
        shiperReceiverInfo.setShipmentInfo(null);
        return this;
    }

    public void setShipperReceiverInfos(Set<ShiperReceiverInfo> shiperReceiverInfos) {
        this.shipperReceiverInfos = shiperReceiverInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentInfo shipmentInfo = (ShipmentInfo) o;
        if (shipmentInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentInfo{" +
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
            "}";
    }
}
