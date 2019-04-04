package com.cargotracker.web.rest;
import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.service.dto.ShipmentTypeDTO;
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

//import static org.elasticsearch.index.query.QueryBuilders.*;

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
     * @param shipmentTypeDTO the shipmentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentTypeDTO, or with status 400 (Bad Request) if the shipmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentTypeDTO> createShipmentType(@Valid @RequestBody ShipmentTypeDTO shipmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ShipmentType : {}", shipmentTypeDTO);
        if (shipmentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentTypeDTO result = shipmentTypeService.save(shipmentTypeDTO);
        return ResponseEntity.created(new URI("/api/shipment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-types : Updates an existing shipmentType.
     *
     * @param shipmentTypeDTO the shipmentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentTypeDTO,
     * or with status 400 (Bad Request) if the shipmentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the shipmentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-types")
    public ResponseEntity<ShipmentTypeDTO> updateShipmentType(@Valid @RequestBody ShipmentTypeDTO shipmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ShipmentType : {}", shipmentTypeDTO);
        if (shipmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentTypeDTO result = shipmentTypeService.save(shipmentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-types : get all the shipmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentTypes in body
     */
    @GetMapping("/shipment-types")
    public List<ShipmentTypeDTO> getAllShipmentTypes() {
        log.debug("REST request to get all ShipmentTypes");
        return shipmentTypeService.findAll();
    }

    /**
     * GET  /shipment-types/:id : get the "id" shipmentType.
     *
     * @param id the id of the shipmentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-types/{id}")
    public ResponseEntity<ShipmentTypeDTO> getShipmentType(@PathVariable Long id) {
        log.debug("REST request to get ShipmentType : {}", id);
        Optional<ShipmentTypeDTO> shipmentTypeDTO = shipmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentTypeDTO);
    }

    /**
     * DELETE  /shipment-types/:id : delete the "id" shipmentType.
     *
     * @param id the id of the shipmentTypeDTO to delete
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
    /*@GetMapping("/_search/shipment-types")
    public List<ShipmentTypeDTO> searchShipmentTypes(@RequestParam String query) {
        log.debug("REST request to search ShipmentTypes for query {}", query);
        return shipmentTypeService.search(query);
    }*/

}
