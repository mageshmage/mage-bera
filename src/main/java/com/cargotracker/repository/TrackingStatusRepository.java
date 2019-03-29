package com.cargotracker.repository;

import com.cargotracker.domain.TrackingStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrackingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingStatusRepository extends JpaRepository<TrackingStatus, Long> {

}
