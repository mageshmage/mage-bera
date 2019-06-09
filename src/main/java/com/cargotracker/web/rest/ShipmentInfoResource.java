package com.cargotracker.web.rest;

import com.cargotracker.service.ShipmentInfoService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.web.rest.util.PaginationUtil;
import com.cargotracker.service.dto.ExcelResponse;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentInformationSearchDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ShipmentInfo.
 */
@RestController
@RequestMapping("/api")
public class ShipmentInfoResource {

	private final Logger log = LoggerFactory.getLogger(ShipmentInfoResource.class);

	private static final String ENTITY_NAME = "shipmentInfo";

	private final ShipmentInfoService shipmentInfoService;

	public ShipmentInfoResource(ShipmentInfoService shipmentInfoService) {
		this.shipmentInfoService = shipmentInfoService;
	}

	/**
	 * POST /shipment-infos : Create a new shipmentInfo.
	 *
	 * @param shipmentInfoDTO
	 *            the shipmentInfoDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         shipmentInfoDTO, or with status 400 (Bad Request) if the shipmentInfo
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/shipment-infos")
	public ResponseEntity<ShipmentInfoDTO> createShipmentInfo(@Valid @RequestBody ShipmentInfoDTO shipmentInfoDTO)
			throws URISyntaxException {
		log.debug("REST request to save ShipmentInfo : {}", shipmentInfoDTO);
		if (shipmentInfoDTO.getId() != null) {
			throw new BadRequestAlertException("A new shipmentInfo cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ShipmentInfoDTO result = shipmentInfoService.save(shipmentInfoDTO);
		return ResponseEntity.created(new URI("/api/shipment-infos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	@PostMapping("/shipment-informations")
	public ResponseEntity<ShipmentInfoDTO> createShipmentInformations(
			@Valid @RequestBody ShipmentInfoDTO shipmentInfoDTO) throws URISyntaxException {
		log.debug("REST request to save ShipmentInfo : {}", shipmentInfoDTO);
		if (shipmentInfoDTO.getId() != null) {
			throw new BadRequestAlertException("A new shipmentInfo cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ShipmentInfoDTO result = null;
		try {
			result = shipmentInfoService.saveShipmentInformation(shipmentInfoDTO);
		} catch (Exception ex) {
			if (ex.getMessage().contains("Consignment Exist")) {
				throw new BadRequestAlertException("Consignment Exist", ENTITY_NAME, "consignmentExists");
			}
		}
		return ResponseEntity.created(new URI("/api/shipment-infos/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);

	}

	/**
	 * PUT /shipment-infos : Updates an existing shipmentInfo.
	 *
	 * @param shipmentInfoDTO
	 *            the shipmentInfoDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         shipmentInfoDTO, or with status 400 (Bad Request) if the
	 *         shipmentInfoDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the shipmentInfoDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/shipment-infos")
	public ResponseEntity<ShipmentInfoDTO> updateShipmentInfo(@Valid @RequestBody ShipmentInfoDTO shipmentInfoDTO)
			throws URISyntaxException {
		log.debug("REST request to update ShipmentInfo : {}", shipmentInfoDTO);
		if (shipmentInfoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ShipmentInfoDTO result = shipmentInfoService.save(shipmentInfoDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentInfoDTO.getId().toString()))
				.body(result);
	}

	@PutMapping("/shipment-informations")
	public ResponseEntity<ShipmentInfoDTO> updateShipmentInformation(
			@Valid @RequestBody ShipmentInfoDTO shipmentInfoDTO) throws URISyntaxException {
		log.debug("REST request to update ShipmentInfo : {}", shipmentInfoDTO);
		if (shipmentInfoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ShipmentInfoDTO result = null;
		try {
			result = shipmentInfoService.saveShipmentInformation(shipmentInfoDTO);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentInfoDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /shipment-infos : get all the shipmentInfos.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of shipmentInfos
	 *         in body
	 */
	@GetMapping("/shipment-infos")
	public ResponseEntity<List<ShipmentInfoDTO>> getAllShipmentInfos(Pageable pageable) {
		log.debug("REST request to get a page of ShipmentInfos");
		Page<ShipmentInfoDTO> page = shipmentInfoService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-infos");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@PostMapping("/shipment-informationsSearch")
	public ResponseEntity<List<ShipmentInfoDTO>> getAllShipmentInformations(Pageable pageable,
			@Valid @RequestBody ShipmentInformationSearchDTO shipmentSearchDto) {
		log.debug("REST request to get a page of ShipmentInfos");
		Page<ShipmentInfoDTO> page = shipmentInfoService.findAllShipmentInformations(pageable, shipmentSearchDto);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-informations");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /shipment-infos/:id : get the "id" shipmentInfo.
	 *
	 * @param id
	 *            the id of the shipmentInfoDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         shipmentInfoDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/shipment-infos/{id}")
	public ResponseEntity<ShipmentInfoDTO> getShipmentInfo(@PathVariable Long id) {
		log.debug("REST request to get ShipmentInfo : {}", id);
		Optional<ShipmentInfoDTO> shipmentInfoDTO = shipmentInfoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(shipmentInfoDTO);
	}

	@GetMapping("/shipment-informations/{id}")
	public ResponseEntity<ShipmentInfoDTO> getShipmentInformation(@PathVariable Long id) {
		log.debug("REST request to get ShipmentInformation : {}", id);
		ShipmentInfoDTO shipmentInfoDTO = shipmentInfoService.findOneShipmentInformation(id);
		// return ResponseUtil.wrapOrNotFound(shipmentInfoDTO);
		return ResponseEntity.ok().body(shipmentInfoDTO);
	}

	/**
	 * DELETE /shipment-infos/:id : delete the "id" shipmentInfo.
	 *
	 * @param id
	 *            the id of the shipmentInfoDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/shipment-infos/{id}")
	public ResponseEntity<Void> deleteShipmentInfo(@PathVariable Long id) {
		log.debug("REST request to delete ShipmentInfo : {}", id);
		shipmentInfoService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@PostMapping("/shipment-informations-bulk")
	public ResponseEntity<ExcelResponse> createShipmentInformationsBulk(@RequestParam MultipartFile file,
			@RequestParam Long vendorId) throws URISyntaxException, IOException {
		log.debug("REST request to save ShipmentInfos Bulk : ");
		if (vendorId == null) {
			throw new BadRequestAlertException("VendorId CanNot be null", ENTITY_NAME, "idexists");
		}
		ExcelResponse result = shipmentInfoService.saveShipmentInformationBulk(file, vendorId);

		if (!result.getIsError()) {
			return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "Bulk Load Done"))
					.body(result);
		} else {
			return ResponseEntity.ok().body(result);
		}
	}

	/**
	 * SEARCH /_search/shipment-infos?query=:query : search for the shipmentInfo
	 * corresponding to the query.
	 *
	 * @param query
	 *            the query of the shipmentInfo search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	/*
	 * @GetMapping("/_search/shipment-infos") public
	 * ResponseEntity<List<ShipmentInfoDTO>> searchShipmentInfos(@RequestParam
	 * String query, Pageable pageable) {
	 * log.debug("REST request to search for a page of ShipmentInfos for query {}",
	 * query); Page<ShipmentInfoDTO> page = shipmentInfoService.search(query,
	 * pageable); HttpHeaders headers =
	 * PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
	 * "/api/_search/shipment-infos"); return
	 * ResponseEntity.ok().headers(headers).body(page.getContent()); }
	 */

}
