package com.cargotracker.repository;

import com.cargotracker.domain.CarrierDetails;
import com.cargotracker.domain.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarrierDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierDetailsRepository extends JpaRepository<CarrierDetails, Long> {

	List<CarrierDetails> findAllByVendor(Vendor vendor);
	
}
