package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {

}
