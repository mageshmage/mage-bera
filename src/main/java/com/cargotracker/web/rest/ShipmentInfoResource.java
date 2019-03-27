package com.cargotracker.web.rest;
import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.service.ShipmentInfoService;
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
     * POST  /shipment-infos : Create a new shipmentInfo.
     *
     * @param shipmentInfo the shipmentInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipmentInfo, or with status 400 (Bad Request) if the shipmentInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipment-infos")
    public ResponseEntity<ShipmentInfo> createShipmentInfo(@Valid @RequestBody ShipmentInfo shipmentInfo) throws URISyntaxException {
        log.debug("REST request to save ShipmentInfo : {}", shipmentInfo);
        if (shipmentInfo.getId() != null) {
            throw new BadRequestAlertException("A new shipmentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentInfo result = shipmentInfoService.save(shipmentInfo);
        return ResponseEntity.created(new URI("/api/shipment-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipment-infos : Updates an existing shipmentInfo.
     *
     * @param shipmentInfo the shipmentInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipmentInfo,
     * or with status 400 (Bad Request) if the shipmentInfo is not valid,
     * or with status 500 (Internal Server Error) if the shipmentInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipment-infos")
    public ResponseEntity<ShipmentInfo> updateShipmentInfo(@Valid @RequestBody ShipmentInfo shipmentInfo) throws URISyntaxException {
        log.debug("REST request to update ShipmentInfo : {}", shipmentInfo);
        if (shipmentInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShipmentInfo result = shipmentInfoService.save(shipmentInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shipmentInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipment-infos : get all the shipmentInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shipmentInfos in body
     */
    @GetMapping("/shipment-infos")
    public ResponseEntity<List<ShipmentInfo>> getAllShipmentInfos(Pageable pageable) {
        log.debug("REST request to get a page of ShipmentInfos");
        Page<ShipmentInfo> page = shipmentInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shipment-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shipment-infos/:id : get the "id" shipmentInfo.
     *
     * @param id the id of the shipmentInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipmentInfo, or with status 404 (Not Found)
     */
    @GetMapping("/shipment-infos/{id}")
    public ResponseEntity<ShipmentInfo> getShipmentInfo(@PathVariable Long id) {
        log.debug("REST request to get ShipmentInfo : {}", id);
        Optional<ShipmentInfo> shipmentInfo = shipmentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentInfo);
    }

    /**
     * DELETE  /shipment-infos/:id : delete the "id" shipmentInfo.
     *
     * @param id the id of the shipmentInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipment-infos/{id}")
    public ResponseEntity<Void> deleteShipmentInfo(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentInfo : {}", id);
        shipmentInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shipment-infos?query=:query : search for the shipmentInfo corresponding
     * to the query.
     *
     * @param query the query of the shipmentInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/shipment-infos")
    public ResponseEntity<List<ShipmentInfo>> searchShipmentInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShipmentInfos for query {}", query);
        Page<ShipmentInfo> page = shipmentInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shipment-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
