package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.domain.Vendor;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentInfoPOD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentInfoPODRepository extends JpaRepository<ShipmentInfoPOD, Long> {
	
	@Query("select s FROM ShipmentInfoPOD s where s.shipmentInfo =:shipmentInfo")
	List<ShipmentInfoPOD> findByShipmentInfo(@Param("shipmentInfo") ShipmentInfo shipmentInfo);

}
