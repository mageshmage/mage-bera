package com.cargotracker.repository;

import com.cargotracker.domain.TrackingStatus;
import com.cargotracker.domain.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrackingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingStatusRepository extends JpaRepository<TrackingStatus, Long> {

	List<TrackingStatus> findAllByVendor(Vendor vendor);
	
}
