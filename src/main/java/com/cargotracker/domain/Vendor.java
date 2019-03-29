package com.cargotracker.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "vendorname", nullable = false)
    private String vendorname;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "pan", nullable = false)
    private String pan;

    @NotNull
    @Column(name = "gst_in", nullable = false)
    private String gstIn;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorname() {
        return vendorname;
    }

    public Vendor vendorname(String vendorname) {
        this.vendorname = vendorname;
        return this;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getFirstName() {
        return firstName;
    }

    public Vendor firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Vendor lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public Vendor mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public Vendor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPan() {
        return pan;
    }

    public Vendor pan(String pan) {
        this.pan = pan;
        return this;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstIn() {
        return gstIn;
    }

    public Vendor gstIn(String gstIn) {
        this.gstIn = gstIn;
        return this;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Vendor isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        Vendor vendor = (Vendor) o;
        if (vendor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", vendorname='" + getVendorname() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", pan='" + getPan() + "'" +
            ", gstIn='" + getGstIn() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
