package com.cargotracker.web.rest;
import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.service.ShipmentTrackingService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.web.rest.util.PaginationUtil;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ShipmentTracking.
 */
@RestController
@RequestMapping("/api")
public class ShipmentTrackingResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentTrackingResource.class);

    private static final String ENTITY_NAME = "shipmentTracking";

    private final ShipmentTrackingService shipmentTrackingService;

    public ShipmentTrackingResource(ShipmentTrackingService shipmentTrackingService) {
        this.shipmentTrackingService = shipmentTrackingService;
    }

    /**
     * POST  /shipment-trackings : Create a new shipmentTracking.
     *
     * @param shipmentTracking the shipmentTracking to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentTracking, or with status 400 (Bad Request) if the shipmentTracking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-trackings")
    public ResponseEntity<ShipmentTracking> createShipmentTracking(@Valid @RequestBody ShipmentTracking shipmentTracking) throws URISyntaxException {
        log.debug("REST request to save ShipmentTracking : {}", shipmentTracking);
        if (shipmentTracking.getId() != null) {
            throw new BadRequestAlertException("A new shipmentTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentTracking result = shipmentTrackingService.save(shipmentTracking);
        return ResponseEntity.created(new URI("/api/shipment-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-trackings : Updates an existing shipmentTracking.
     *
     * @param shipmentTracking the shipmentTracking to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentTracking,
     * or with status 400 (Bad Request) if the shipmentTracking is not valid,
     * or with status 500 (Internal Server Error) if the shipmentTracking couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-trackings")
    public ResponseEntity<ShipmentTracking> updateShipmentTracking(@Valid @RequestBody ShipmentTracking shipmentTracking) throws URISyntaxException {
        log.debug("REST request to update ShipmentTracking : {}", shipmentTracking);
        if (shipmentTracking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentTracking result = shipmentTrackingService.save(shipmentTracking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentTracking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-trackings : get all the shipmentTrackings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentTrackings in body
     */
    @GetMapping("/shipment-trackings")
    public ResponseEntity<List<ShipmentTracking>> getAllShipmentTrackings(Pageable pageable) {
        log.debug("REST request to get a page of ShipmentTrackings");
        Page<ShipmentTracking> page = shipmentTrackingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-trackings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shipment-trackings/:id : get the "id" shipmentTracking.
     *
     * @param id the id of the shipmentTracking to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentTracking, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-trackings/{id}")
    public ResponseEntity<ShipmentTracking> getShipmentTracking(@PathVariable Long id) {
        log.debug("REST request to get ShipmentTracking : {}", id);
        Optional<ShipmentTracking> shipmentTracking = shipmentTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentTracking);
    }

    /**
     * DELETE  /shipment-trackings/:id : delete the "id" shipmentTracking.
     *
     * @param id the id of the shipmentTracking to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-trackings/{id}")
    public ResponseEntity<Void> deleteShipmentTracking(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentTracking : {}", id);
        shipmentTrackingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-trackings?query=:query : search for the shipmentTracking corresponding
     * to the query.
     *
     * @param query the query of the shipmentTracking search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-trackings")
    public ResponseEntity<List<ShipmentTracking>> searchShipmentTrackings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShipmentTrackings for query {}", query);
        Page<ShipmentTracking> page = shipmentTrackingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shipment-trackings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
