package com.cargotracker.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentMode.
 */
@Entity
@Table(name = "payment_mode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paymentmode")
public class PaymentMode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToOne
    @JoinColumn(unique = true)
    private Vendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public PaymentMode value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public PaymentMode desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public PaymentMode vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
        PaymentMode paymentMode = (PaymentMode) o;
        if (paymentMode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentMode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentMode{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
