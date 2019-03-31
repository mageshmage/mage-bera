package com.cargotracker.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ShipmentInfoPOD entity.
 */
public class ShipmentInfoPODDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] pod;

    private String podContentType;
    private String comments;


    private Long shipmentInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPod() {
        return pod;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

        ShipmentInfoPODDTO shipmentInfoPODDTO = (ShipmentInfoPODDTO) o;
        if (shipmentInfoPODDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipmentInfoPODDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipmentInfoPODDTO{" +
            "id=" + getId() +
            ", pod='" + getPod() + "'" +
            ", comments='" + getComments() + "'" +
            ", shipmentInfo=" + getShipmentInfoId() +
            "}";
    }
}
