package com.cargotracker.web.rest;
import com.cargotracker.domain.ShipmentType;
import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ShipmentType.
 */
@RestController
@RequestMapping("/api")
public class ShipmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentTypeResource.class);

    private static final String ENTITY_NAME = "shipmentType";

    private final ShipmentTypeService shipmentTypeService;

    public ShipmentTypeResource(ShipmentTypeService shipmentTypeService) {
        this.shipmentTypeService = shipmentTypeService;
    }

    /**
     * POST  /shipment-types : Create a new shipmentType.
     *
     * @param shipmentType the shipmentType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentType, or with status 400 (Bad Request) if the shipmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentType> createShipmentType(@Valid @RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to save ShipmentType : {}", shipmentType);
        if (shipmentType.getId() != null) {
            throw new BadRequestAlertException("A new shipmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentType result = shipmentTypeService.save(shipmentType);
        return ResponseEntity.created(new URI("/api/shipment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-types : Updates an existing shipmentType.
     *
     * @param shipmentType the shipmentType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentType,
     * or with status 400 (Bad Request) if the shipmentType is not valid,
     * or with status 500 (Internal Server Error) if the shipmentType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-types")
    public ResponseEntity<ShipmentType> updateShipmentType(@Valid @RequestBody ShipmentType shipmentType) throws URISyntaxException {
        log.debug("REST request to update ShipmentType : {}", shipmentType);
        if (shipmentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentType result = shipmentTypeService.save(shipmentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-types : get all the shipmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentTypes in body
     */
    @GetMapping("/shipment-types")
    public List<ShipmentType> getAllShipmentTypes() {
        log.debug("REST request to get all ShipmentTypes");
        return shipmentTypeService.findAll();
    }

    /**
     * GET  /shipment-types/:id : get the "id" shipmentType.
     *
     * @param id the id of the shipmentType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentType, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-types/{id}")
    public ResponseEntity<ShipmentType> getShipmentType(@PathVariable Long id) {
        log.debug("REST request to get ShipmentType : {}", id);
        Optional<ShipmentType> shipmentType = shipmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentType);
    }

    /**
     * DELETE  /shipment-types/:id : delete the "id" shipmentType.
     *
     * @param id the id of the shipmentType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-types/{id}")
    public ResponseEntity<Void> deleteShipmentType(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentType : {}", id);
        shipmentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-types?query=:query : search for the shipmentType corresponding
     * to the query.
     *
     * @param query the query of the shipmentType search
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-types")
    public List<ShipmentType> searchShipmentTypes(@RequestParam String query) {
        log.debug("REST request to search ShipmentTypes for query {}", query);
        return shipmentTypeService.search(query);
    }

}
