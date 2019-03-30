package com.cargotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ShipmentTracking.
 */
@Entity
@Table(name = "shipment_tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shipmenttracking")
public class ShipmentTracking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tracking_date", nullable = false)
    private ZonedDateTime trackingDate;

    @NotNull
    @Column(name = "place", nullable = false)
    private String place;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("shipmentTrackings")
    private ShipmentInfo shipmentInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTrackingDate() {
        return trackingDate;
    }

    public ShipmentTracking trackingDate(ZonedDateTime trackingDate) {
        this.trackingDate = trackingDate;
        return this;
    }

    public void setTrackingDate(ZonedDateTime trackingDate) {
        this.trackingDate = trackingDate;
    }

    public String getPlace() {
        return place;
    }

    public ShipmentTracking place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public ShipmentTracking status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShipmentInfo getShipmentInfo() {
        return shipmentInfo;
    }

    public ShipmentTracking shipmentInfo(ShipmentInfo shipmentInfo) {
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
        ShipmentTracking shipmentTracking = (ShipmentTracking) o;
        if (shipmentTracking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentTracking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentTracking{" +
            "id=" + getId() +
            ", trackingDate='" + getTrackingDate() + "'" +
            ", place='" + getPlace() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
