package com.cargotracker.web.rest;
import com.cargotracker.service.ShipmentInfoPODService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.service.dto.ShipmentInfoPODDTO;
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
     * @param shipmentInfoPODDTO the shipmentInfoPODDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentInfoPODDTO, or with status 400 (Bad Request) if the shipmentInfoPOD has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-info-pods")
    public ResponseEntity<ShipmentInfoPODDTO> createShipmentInfoPOD(@RequestBody ShipmentInfoPODDTO shipmentInfoPODDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentInfoPOD : {}", shipmentInfoPODDTO);
        if (shipmentInfoPODDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentInfoPOD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentInfoPODDTO result = shipmentInfoPODService.save(shipmentInfoPODDTO);
        return ResponseEntity.created(new URI("/api/shipment-info-pods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-info-pods : Updates an existing shipmentInfoPOD.
     *
     * @param shipmentInfoPODDTO the shipmentInfoPODDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentInfoPODDTO,
     * or with status 400 (Bad Request) if the shipmentInfoPODDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentInfoPODDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-info-pods")
    public ResponseEntity<ShipmentInfoPODDTO> updateShipmentInfoPOD(@RequestBody ShipmentInfoPODDTO shipmentInfoPODDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentInfoPOD : {}", shipmentInfoPODDTO);
        if (shipmentInfoPODDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentInfoPODDTO result = shipmentInfoPODService.save(shipmentInfoPODDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentInfoPODDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-info-pods : get all the shipmentInfoPODS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentInfoPODS in body
     */
    @GetMapping("/shipment-info-pods")
    public List<ShipmentInfoPODDTO> getAllShipmentInfoPODS() {
        log.debug("REST request to get all ShipmentInfoPODS");
        return shipmentInfoPODService.findAll();
    }

    /**
     * GET  /shipment-info-pods/:id : get the "id" shipmentInfoPOD.
     *
     * @param id the id of the shipmentInfoPODDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentInfoPODDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-info-pods/{id}")
    public ResponseEntity<ShipmentInfoPODDTO> getShipmentInfoPOD(@PathVariable Long id) {
        log.debug("REST request to get ShipmentInfoPOD : {}", id);
        Optional<ShipmentInfoPODDTO> shipmentInfoPODDTO = shipmentInfoPODService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentInfoPODDTO);
    }

    /**
     * DELETE  /shipment-info-pods/:id : delete the "id" shipmentInfoPOD.
     *
     * @param id the id of the shipmentInfoPODDTO to delete
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
    public List<ShipmentInfoPODDTO> searchShipmentInfoPODS(@RequestParam String query) {
        log.debug("REST request to search ShipmentInfoPODS for query {}", query);
        return shipmentInfoPODService.search(query);
    }

}
