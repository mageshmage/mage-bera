package com.cargotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

//import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.cargotracker.domain.enumeration.ShiperReceiverType;

/**
 * A ShiperReceiverInfo.
 */
@Entity
@Table(name = "shiper_receiver_info")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//@Document(indexName = "shiperreceiverinfo")
public class ShiperReceiverInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private ShiperReceiverType type;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "pincode", nullable = false)
    private String pincode;

    @Column(name = "email_id")
    private String emailId;

    @ManyToOne
    @JsonIgnoreProperties("shipperReceiverInfos")
    private ShipmentInfo shipmentInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShiperReceiverType getType() {
        return type;
    }

    public ShiperReceiverInfo type(ShiperReceiverType type) {
        this.type = type;
        return this;
    }

    public void setType(ShiperReceiverType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ShiperReceiverInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public ShiperReceiverInfo phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public ShiperReceiverInfo address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public ShiperReceiverInfo city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public ShiperReceiverInfo pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmailId() {
        return emailId;
    }

    public ShiperReceiverInfo emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public ShipmentInfo getShipmentInfo() {
        return shipmentInfo;
    }

    public ShiperReceiverInfo shipmentInfo(ShipmentInfo shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
        return this;
    }

    public void setShipmentInfo(ShipmentInfo shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
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
        ShiperReceiverInfo shiperReceiverInfo = (ShiperReceiverInfo) o;
        if (shiperReceiverInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shiperReceiverInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShiperReceiverInfo{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", emailId='" + getEmailId() + "'" +
            "}";
    }
}
