package com.cargotracker.repository;

import com.cargotracker.domain.ShiperReceiverInfo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShiperReceiverInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShiperReceiverInfoRepository extends JpaRepository<ShiperReceiverInfo, Long> {

	List<ShiperReceiverInfo> findByIdIn(List<Long> ids);

	List<ShiperReceiverInfo> findByShipmentInfoIn(List<Long> ids);

}
