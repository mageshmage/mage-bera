package com.cargotracker.repository;

import com.cargotracker.domain.PaymentMode;
import com.cargotracker.domain.Vendor;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {
	
	List<PaymentMode> findAllByVendor(Vendor vendor);

}
