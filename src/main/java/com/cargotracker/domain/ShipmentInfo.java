package com.cargotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.cargotracker.domain.enumeration.CARRIER;

import com.cargotracker.domain.enumeration.ShipmentType;

import com.cargotracker.domain.enumeration.ShipmentMode;

import com.cargotracker.domain.enumeration.PaymentMode;

import com.cargotracker.domain.enumeration.Status;

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

    @Column(name = "is_third_party")
    private Boolean isThirdParty;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private CARRIER carrier;

    @Column(name = "courier")
    private String courier;

    @Column(name = "carrier_ref_no")
    private String carrierRefNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shipment_type", nullable = false)
    private ShipmentType shipmentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shipment_mode", nullable = false)
    private ShipmentMode shipmentMode;

    @NotNull
    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;

    @NotNull
    @Column(name = "expected_delivery_date", nullable = false)
    private LocalDate expectedDeliveryDate;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_fright", precision = 10, scale = 2)
    private BigDecimal totalFright;

    @Column(name = "package_desciption")
    private String packageDesciption;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMode paymentMode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "comments")
    private String comments;

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
    private Set<ShiperReceiverInfo> shipperREceiverInfos = new HashSet<>();
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

    public CARRIER getCarrier() {
        return carrier;
    }

    public ShipmentInfo carrier(CARRIER carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(CARRIER carrier) {
        this.carrier = carrier;
    }

    public String getCourier() {
        return courier;
    }

    public ShipmentInfo courier(String courier) {
        this.courier = courier;
        return this;
    }

    public void setCourier(String courier) {
        this.courier = courier;
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

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public ShipmentInfo pickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
        return this;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public ShipmentInfo expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public ShipmentInfo weight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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

    public Status getStatus() {
        return status;
    }

    public ShipmentInfo status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public ShipmentInfo comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Set<ShiperReceiverInfo> getShipperREceiverInfos() {
        return shipperREceiverInfos;
    }

    public ShipmentInfo shipperREceiverInfos(Set<ShiperReceiverInfo> shiperReceiverInfos) {
        this.shipperREceiverInfos = shiperReceiverInfos;
        return this;
    }

    public ShipmentInfo addShipperREceiverInfo(ShiperReceiverInfo shiperReceiverInfo) {
        this.shipperREceiverInfos.add(shiperReceiverInfo);
        shiperReceiverInfo.setShipmentInfo(this);
        return this;
    }

    public ShipmentInfo removeShipperREceiverInfo(ShiperReceiverInfo shiperReceiverInfo) {
        this.shipperREceiverInfos.remove(shiperReceiverInfo);
        shiperReceiverInfo.setShipmentInfo(null);
        return this;
    }

    public void setShipperREceiverInfos(Set<ShiperReceiverInfo> shiperReceiverInfos) {
        this.shipperREceiverInfos = shiperReceiverInfos;
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
            ", isThirdParty='" + isIsThirdParty() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", courier='" + getCourier() + "'" +
            ", carrierRefNo='" + getCarrierRefNo() + "'" +
            ", shipmentType='" + getShipmentType() + "'" +
            ", shipmentMode='" + getShipmentMode() + "'" +
            ", pickupDate='" + getPickupDate() + "'" +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", weight=" + getWeight() +
            ", quantity=" + getQuantity() +
            ", totalFright=" + getTotalFright() +
            ", packageDesciption='" + getPackageDesciption() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", status='" + getStatus() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
