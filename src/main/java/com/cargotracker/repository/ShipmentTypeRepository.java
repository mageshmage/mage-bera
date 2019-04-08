package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentType;
import com.cargotracker.domain.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {
	
	List<ShipmentType> findAllByVendor(Vendor vendor);

}
