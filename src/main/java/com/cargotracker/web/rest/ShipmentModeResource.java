package com.cargotracker.web.rest;
import com.cargotracker.service.ShipmentModeService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.service.dto.ShipmentModeDTO;
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
 * REST controller for managing ShipmentMode.
 */
@RestController
@RequestMapping("/api")
public class ShipmentModeResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentModeResource.class);

    private static final String ENTITY_NAME = "shipmentMode";

    private final ShipmentModeService shipmentModeService;

    public ShipmentModeResource(ShipmentModeService shipmentModeService) {
        this.shipmentModeService = shipmentModeService;
    }

    /**
     * POST  /shipment-modes : Create a new shipmentMode.
     *
     * @param shipmentModeDTO the shipmentModeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentModeDTO, or with status 400 (Bad Request) if the shipmentMode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-modes")
    public ResponseEntity<ShipmentModeDTO> createShipmentMode(@Valid @RequestBody ShipmentModeDTO shipmentModeDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentMode : {}", shipmentModeDTO);
        if (shipmentModeDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentMode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentModeDTO result = shipmentModeService.save(shipmentModeDTO);
        return ResponseEntity.created(new URI("/api/shipment-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-modes : Updates an existing shipmentMode.
     *
     * @param shipmentModeDTO the shipmentModeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentModeDTO,
     * or with status 400 (Bad Request) if the shipmentModeDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentModeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-modes")
    public ResponseEntity<ShipmentModeDTO> updateShipmentMode(@Valid @RequestBody ShipmentModeDTO shipmentModeDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentMode : {}", shipmentModeDTO);
        if (shipmentModeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentModeDTO result = shipmentModeService.save(shipmentModeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentModeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-modes : get all the shipmentModes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentModes in body
     */
    @GetMapping("/shipment-modes")
    public List<ShipmentModeDTO> getAllShipmentModes() {
        log.debug("REST request to get all ShipmentModes");
        return shipmentModeService.findAll();
    }

    /**
     * GET  /shipment-modes/:id : get the "id" shipmentMode.
     *
     * @param id the id of the shipmentModeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentModeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-modes/{id}")
    public ResponseEntity<ShipmentModeDTO> getShipmentMode(@PathVariable Long id) {
        log.debug("REST request to get ShipmentMode : {}", id);
        Optional<ShipmentModeDTO> shipmentModeDTO = shipmentModeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentModeDTO);
    }

    /**
     * DELETE  /shipment-modes/:id : delete the "id" shipmentMode.
     *
     * @param id the id of the shipmentModeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-modes/{id}")
    public ResponseEntity<Void> deleteShipmentMode(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentMode : {}", id);
        shipmentModeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-modes?query=:query : search for the shipmentMode corresponding
     * to the query.
     *
     * @param query the query of the shipmentMode search
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-modes")
    public List<ShipmentModeDTO> searchShipmentModes(@RequestParam String query) {
        log.debug("REST request to search ShipmentModes for query {}", query);
        return shipmentModeService.search(query);
    }

}
