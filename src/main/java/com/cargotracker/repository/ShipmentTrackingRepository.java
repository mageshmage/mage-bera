package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentTracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, Long> {

}
