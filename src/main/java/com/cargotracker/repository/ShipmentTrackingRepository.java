package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.ShipmentTracking;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, Long> {

	@Query("select s FROM ShipmentTracking s where s.shipmentInfo =:shipmentInfo order by s.trackingDate desc")
	List<ShipmentTracking> findByShipmentInfo(@Param("shipmentInfo") ShipmentInfo shipmentInfo);

}
