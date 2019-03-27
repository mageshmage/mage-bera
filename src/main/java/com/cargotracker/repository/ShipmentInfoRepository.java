package com.cargotracker.repository;

import com.cargotracker.domain.ShipmentInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentInfoRepository extends JpaRepository<ShipmentInfo, Long> {

}
