package com.cargotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ShipmentInfoPOD.
 */
@Entity
@Table(name = "shipment_info_pod")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "shipmentinfopod")
public class ShipmentInfoPOD implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "pod")
    private byte[] pod;

    @Column(name = "pod_content_type")
    private String podContentType;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JsonIgnoreProperties("shipmentInfoPODS")
    private ShipmentInfo shipmentInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPod() {
        return pod;
    }

    public ShipmentInfoPOD pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public ShipmentInfoPOD podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public String getComments() {
        return comments;
    }

    public ShipmentInfoPOD comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ShipmentInfo getShipmentInfo() {
        return shipmentInfo;
    }

    public ShipmentInfoPOD shipmentInfo(ShipmentInfo shipmentInfo) {
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
        ShipmentInfoPOD shipmentInfoPOD = (ShipmentInfoPOD) o;
        if (shipmentInfoPOD.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentInfoPOD.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentInfoPOD{" +
            "id=" + getId() +
            ", pod='" + getPod() + "'" +
            ", podContentType='" + getPodContentType() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
