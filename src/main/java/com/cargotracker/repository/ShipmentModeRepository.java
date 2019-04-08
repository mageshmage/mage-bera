package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentMode;
import com.cargotracker.domain.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentModeRepository extends JpaRepository<ShipmentMode, Long> {
	
	List<ShipmentMode> findAllByVendor(Vendor vendor);

}
