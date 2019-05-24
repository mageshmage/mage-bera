package com.cargotracker.web.rest;

import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.service.ShipmentTrackingService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.web.rest.util.PaginationUtil;
import com.cargotracker.service.dto.ShipmentInfoDTO;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ShipmentTracking.
 */
@RestController
@RequestMapping("/api")
public class ShipmentTrackingResource {

	private final Logger log = LoggerFactory.getLogger(ShipmentTrackingResource.class);

	private static final String ENTITY_NAME = "shipmentTracking";

	private final ShipmentTrackingService shipmentTrackingService;

	private final ShipmentInfoRepository shipmentInfoRepository;

	public ShipmentTrackingResource(ShipmentTrackingService shipmentTrackingService,
			ShipmentInfoRepository shipmentInfoRepository) {
		this.shipmentTrackingService = shipmentTrackingService;
		this.shipmentInfoRepository = shipmentInfoRepository;
	}

	/**
	 * POST /shipment-trackings : Create a new shipmentTracking.
	 *
	 * @param shipmentTrackingDTO
	 *            the shipmentTrackingDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         shipmentTrackingDTO, or with status 400 (Bad Request) if the
	 *         shipmentTracking has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/shipment-trackings")
	public ResponseEntity<ShipmentTrackingDTO> createShipmentTracking(
			@Valid @RequestBody ShipmentTrackingDTO shipmentTrackingDTO) throws URISyntaxException {
		log.debug("REST request to save ShipmentTracking : {}", shipmentTrackingDTO);
		if (shipmentTrackingDTO.getId() != null) {
			throw new BadRequestAlertException("A new shipmentTracking cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		if (shipmentTrackingDTO.getShipmentInfoId() == null) {
			throw new BadRequestAlertException("A new shipmentTracking Should have an ShipmentInfo Reference",
					ENTITY_NAME, "idexists");
		}
		ShipmentTrackingDTO result = shipmentTrackingService.save(shipmentTrackingDTO);
		return ResponseEntity.created(new URI("/api/shipment-trackings/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /shipment-trackings : Updates an existing shipmentTracking.
	 *
	 * @param shipmentTrackingDTO
	 *            the shipmentTrackingDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         shipmentTrackingDTO, or with status 400 (Bad Request) if the
	 *         shipmentTrackingDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the shipmentTrackingDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/shipment-trackings")
	public ResponseEntity<ShipmentTrackingDTO> updateShipmentTracking(
			@Valid @RequestBody ShipmentTrackingDTO shipmentTrackingDTO) throws URISyntaxException {
		log.debug("REST request to update ShipmentTracking : {}", shipmentTrackingDTO);
		if (shipmentTrackingDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ShipmentTrackingDTO result = shipmentTrackingService.save(shipmentTrackingDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentTrackingDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /shipment-trackings : get all the shipmentTrackings.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         shipmentTrackings in body
	 */
	@GetMapping("/shipment-trackings")
	public ResponseEntity<List<ShipmentTrackingDTO>> getAllShipmentTrackings(Pageable pageable) {
		log.debug("REST request to get a page of ShipmentTrackings");
		Page<ShipmentTrackingDTO> page = shipmentTrackingService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-trackings");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /shipment-trackings/:id : get the "id" shipmentTracking.
	 *
	 * @param id
	 *            the id of the shipmentTrackingDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         shipmentTrackingDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/shipment-trackings/{id}")
	public ResponseEntity<ShipmentTrackingDTO> getShipmentTracking(@PathVariable Long id) {
		log.debug("REST request to get ShipmentTracking : {}", id);
		Optional<ShipmentTrackingDTO> shipmentTrackingDTO = shipmentTrackingService.findOne(id);
		ShipmentInfo shipmentInfo = shipmentInfoRepository.findById(shipmentTrackingDTO.get().getShipmentInfoId())
				.get();
		ShipmentTrackingDTO dto = shipmentTrackingDTO.get();
		dto.setIsDelivered(shipmentInfo.getIsDelivered() != null ? shipmentInfo.getIsDelivered() : false);
		dto.setIsInTransit(shipmentInfo.getIsInTransit() != null ? shipmentInfo.getIsInTransit() : false);
		dto.setIsReachedNearestHub(
				shipmentInfo.getIsReachedNearestHub() != null ? shipmentInfo.getIsReachedNearestHub() : false);
		dto.setIsOutForDelivery(shipmentInfo.getIsOutForDelivery() != null ? shipmentInfo.getIsOutForDelivery() : false);
		dto.setReceivedBy(shipmentInfo.getReceivedBy());
		dto.setRelationShip(shipmentInfo.getRelationShip());
		// return ResponseUtil.wrapOrNotFound(shipmentTrackingDTO);
		return ResponseEntity.ok().body(dto);
	}

	/**
	 * DELETE /shipment-trackings/:id : delete the "id" shipmentTracking.
	 *
	 * @param id
	 *            the id of the shipmentTrackingDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/shipment-trackings/{id}")
	public ResponseEntity<Void> deleteShipmentTracking(@PathVariable Long id) {
		log.debug("REST request to delete ShipmentTracking : {}", id);
		shipmentTrackingService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/shipment-trackings?query=:query : search for the
	 * shipmentTracking corresponding to the query.
	 *
	 * @param query
	 *            the query of the shipmentTracking search
	 * @param pageable
	 *            the pagination information
	 * @return the result of the search
	 */
	/*
	 * @GetMapping("/_search/shipment-trackings") public
	 * ResponseEntity<List<ShipmentTrackingDTO>>
	 * searchShipmentTrackings(@RequestParam String query, Pageable pageable) { log.
	 * debug("REST request to search for a page of ShipmentTrackings for query {}",
	 * query); Page<ShipmentTrackingDTO> page =
	 * shipmentTrackingService.search(query, pageable); HttpHeaders headers =
	 * PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
	 * "/api/_search/shipment-trackings"); return
	 * ResponseEntity.ok().headers(headers).body(page.getContent()); }
	 */

	@GetMapping("/shipment-trackingssearch")
	public ResponseEntity<List<ShipmentTrackingDTO>> searchShipmentTrackings(@RequestParam String query,
			@RequestParam Long vendorId, Pageable pageable) {
		log.debug("REST request to search for a page of ShipmentTrackings for query {}", query);
		List<ShipmentTrackingDTO> page = shipmentTrackingService.searchConsignment(query, vendorId);
		// HttpHeaders headers =
		// PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
		// "/api/_search/shipment-trackings");
		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/shipment-trackingssearchpublic")
	public ResponseEntity<ShipmentInfoDTO> searchShipmentTrackingsPublic(@RequestParam String query) {
		log.debug("REST request to search for a page of searchShipmentTrackingsPublic for query {}", query);
		ShipmentInfoDTO page = shipmentTrackingService.searchConsignmentPublic(query);
		// HttpHeaders headers =
		// PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
		// "/api/_search/shipment-trackings");
		if(page.getId() == null)
			throw new BadRequestAlertException("No TrakingId Found", ENTITY_NAME,
					"noConsignmentResult");
		
		return ResponseEntity.ok().body(page);
	}
	
	@GetMapping("/shipment-trackings-autoFillNewTracking")
	public ResponseEntity<ShipmentTrackingDTO> autoFillNewTracking(@RequestParam String query,
			@RequestParam Long vendorId) {
		log.debug("REST request to auto fill ShipmentTrackings for query {}", query);
		ShipmentTrackingDTO page = shipmentTrackingService.autoFillNewTracking(query, vendorId);
		// HttpHeaders headers =
		// PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
		// "/api/_search/shipment-trackings");
		return ResponseEntity.ok().body(page);
	}
	

}
