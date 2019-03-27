package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentInfoPOD;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentInfoPOD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentInfoPODRepository extends JpaRepository<ShipmentInfoPOD, Long> {

}
