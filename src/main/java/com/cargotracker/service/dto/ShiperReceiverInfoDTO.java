package com.cargotracker.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.cargotracker.domain.enumeration.ShiperReceiverType;

/**
 * A DTO for the ShiperReceiverInfo entity.
 */
public class ShiperReceiverInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private ShiperReceiverType type;

    @NotNull
    private String name;

    @NotNull
    private String phoneNo;

    @NotNull
    private String address;

    private String city;

    @NotNull
    private String pincode;

    private String emailId;


    private Long shipmentInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShiperReceiverType getType() {
        return type;
    }

    public void setType(ShiperReceiverType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getShipmentInfoId() {
        return shipmentInfoId;
    }

    public void setShipmentInfoId(Long shipmentInfoId) {
        this.shipmentInfoId = shipmentInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShiperReceiverInfoDTO shiperReceiverInfoDTO = (ShiperReceiverInfoDTO) o;
        if (shiperReceiverInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shiperReceiverInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShiperReceiverInfoDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", shipmentInfo=" + getShipmentInfoId() +
            "}";
    }
}
