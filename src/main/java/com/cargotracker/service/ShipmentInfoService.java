package com.cargotracker.service;

import com.cargotracker.service.dto.ExcelResponse;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service Interface for managing ShipmentInfo.
 */
public interface ShipmentInfoService {

    /**
     * Save a shipmentInfo.
     *
     * @param shipmentInfoDTO the entity to save
     * @return the persisted entity
     */
    ShipmentInfoDTO save(ShipmentInfoDTO shipmentInfoDTO);
    
    ShipmentInfoDTO saveShipmentInformation(ShipmentInfoDTO shipmentInfoDTO) throws Exception;
    
    ExcelResponse saveShipmentInformationBulk(MultipartFile file, Long vendorId) throws IOException;

    /**
     * Get all the shipmentInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShipmentInfoDTO> findAll(Pageable pageable);
    
    Page<ShipmentInfoDTO> findAllShipmentInformations(Pageable pageable, ShipmentInformationSearchDTO shipmentSearchDto);


    /**
     * Get the "id" shipmentInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShipmentInfoDTO> findOne(Long id);
    
    public ShipmentInfoDTO findOneShipmentInformation(Long id);

    /**
     * Delete the "id" shipmentInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shipmentInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    //Page<ShipmentInfoDTO> search(String query, Pageable pageable);
}
