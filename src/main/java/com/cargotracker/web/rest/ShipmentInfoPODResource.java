package com.cargotracker.web.rest;
import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.service.ShipmentInfoPODService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ShipmentInfoPOD.
 */
@RestController
@RequestMapping("/api")
public class ShipmentInfoPODResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentInfoPODResource.class);

    private static final String ENTITY_NAME = "shipmentInfoPOD";

    private final ShipmentInfoPODService shipmentInfoPODService;

    public ShipmentInfoPODResource(ShipmentInfoPODService shipmentInfoPODService) {
        this.shipmentInfoPODService = shipmentInfoPODService;
    }

    /**
     * POST  /shipment-info-pods : Create a new shipmentInfoPOD.
     *
     * @param shipmentInfoPOD the shipmentInfoPOD to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentInfoPOD, or with status 400 (Bad Request) if the shipmentInfoPOD has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-info-pods")
    public ResponseEntity<ShipmentInfoPOD> createShipmentInfoPOD(@RequestBody ShipmentInfoPOD shipmentInfoPOD) throws URISyntaxException {
        log.debug("REST request to save ShipmentInfoPOD : {}", shipmentInfoPOD);
        if (shipmentInfoPOD.getId() != null) {
            throw new BadRequestAlertException("A new shipmentInfoPOD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentInfoPOD result = shipmentInfoPODService.save(shipmentInfoPOD);
        return ResponseEntity.created(new URI("/api/shipment-info-pods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-info-pods : Updates an existing shipmentInfoPOD.
     *
     * @param shipmentInfoPOD the shipmentInfoPOD to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentInfoPOD,
     * or with status 400 (Bad Request) if the shipmentInfoPOD is not valid,
     * or with status 500 (Internal Server Error) if the shipmentInfoPOD couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-info-pods")
    public ResponseEntity<ShipmentInfoPOD> updateShipmentInfoPOD(@RequestBody ShipmentInfoPOD shipmentInfoPOD) throws URISyntaxException {
        log.debug("REST request to update ShipmentInfoPOD : {}", shipmentInfoPOD);
        if (shipmentInfoPOD.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentInfoPOD result = shipmentInfoPODService.save(shipmentInfoPOD);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentInfoPOD.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-info-pods : get all the shipmentInfoPODS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentInfoPODS in body
     */
    @GetMapping("/shipment-info-pods")
    public List<ShipmentInfoPOD> getAllShipmentInfoPODS() {
        log.debug("REST request to get all ShipmentInfoPODS");
        return shipmentInfoPODService.findAll();
    }

    /**
     * GET  /shipment-info-pods/:id : get the "id" shipmentInfoPOD.
     *
     * @param id the id of the shipmentInfoPOD to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentInfoPOD, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-info-pods/{id}")
    public ResponseEntity<ShipmentInfoPOD> getShipmentInfoPOD(@PathVariable Long id) {
        log.debug("REST request to get ShipmentInfoPOD : {}", id);
        Optional<ShipmentInfoPOD> shipmentInfoPOD = shipmentInfoPODService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentInfoPOD);
    }

    /**
     * DELETE  /shipment-info-pods/:id : delete the "id" shipmentInfoPOD.
     *
     * @param id the id of the shipmentInfoPOD to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-info-pods/{id}")
    public ResponseEntity<Void> deleteShipmentInfoPOD(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentInfoPOD : {}", id);
        shipmentInfoPODService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-info-pods?query=:query : search for the shipmentInfoPOD corresponding
     * to the query.
     *
     * @param query the query of the shipmentInfoPOD search
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-info-pods")
    public List<ShipmentInfoPOD> searchShipmentInfoPODS(@RequestParam String query) {
        log.debug("REST request to search ShipmentInfoPODS for query {}", query);
        return shipmentInfoPODService.search(query);
    }

}
