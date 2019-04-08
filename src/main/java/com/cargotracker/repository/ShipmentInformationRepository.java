package com.cargotracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cargotracker.domain.ShipmentInfo;

public interface ShipmentInformationRepository extends JpaRepository<ShipmentInfo, Long>, CustomShipmentInformationRepository{

}
