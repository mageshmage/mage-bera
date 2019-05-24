package com.cargotracker.repository;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.domain.Vendor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentInfoRepository extends JpaRepository<ShipmentInfo, Long> {

	@Query("select s FROM ShipmentInfo s where s.consignmentNo =:consignmentNo and s.vendor =:vendor")
	List<ShipmentInfo> findByConsignmentNoAndVendor(@Param("consignmentNo") String consignmentNo, @Param("vendor") Vendor vendor);
	
	@Query("select s FROM ShipmentInfo s where s.consignmentNo =:consignmentNo")
	List<ShipmentInfo> findByConsignmentNo(@Param("consignmentNo") String consignmentNo);

}
