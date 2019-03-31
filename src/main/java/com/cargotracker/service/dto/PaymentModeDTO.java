package com.cargotracker.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PaymentMode entity.
 */
public class PaymentModeDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    private String desc;


    private Long vendorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentModeDTO paymentModeDTO = (PaymentModeDTO) o;
        if (paymentModeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentModeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentModeDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", desc='" + getDesc() + "'" +
            ", vendor=" + getVendorId() +
            "}";
    }
}
