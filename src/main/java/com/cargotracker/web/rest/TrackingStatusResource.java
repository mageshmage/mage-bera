package com.cargotracker.web.rest;
import com.cargotracker.service.TrackingStatusService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.service.dto.PaymentModeDTO;
import com.cargotracker.service.dto.TrackingStatusDTO;
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
 * REST controller for managing TrackingStatus.
 */
@RestController
@RequestMapping("/api")
public class TrackingStatusResource {

    private final Logger log = LoggerFactory.getLogger(TrackingStatusResource.class);

    private static final String ENTITY_NAME = "trackingStatus";

    private final TrackingStatusService trackingStatusService;

    public TrackingStatusResource(TrackingStatusService trackingStatusService) {
        this.trackingStatusService = trackingStatusService;
    }

    /**
     * POST  /tracking-statuses : Create a new trackingStatus.
     *
     * @param trackingStatusDTO the trackingStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trackingStatusDTO, or with status 400 (Bad Request) if the trackingStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tracking-statuses")
    public ResponseEntity<TrackingStatusDTO> createTrackingStatus(@Valid @RequestBody TrackingStatusDTO trackingStatusDTO) throws URISyntaxException {
        log.debug("REST request to save TrackingStatus : {}", trackingStatusDTO);
        if (trackingStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new trackingStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrackingStatusDTO result = trackingStatusService.save(trackingStatusDTO);
        return ResponseEntity.created(new URI("/api/tracking-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tracking-statuses : Updates an existing trackingStatus.
     *
     * @param trackingStatusDTO the trackingStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trackingStatusDTO,
     * or with status 400 (Bad Request) if the trackingStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the trackingStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tracking-statuses")
    public ResponseEntity<TrackingStatusDTO> updateTrackingStatus(@Valid @RequestBody TrackingStatusDTO trackingStatusDTO) throws URISyntaxException {
        log.debug("REST request to update TrackingStatus : {}", trackingStatusDTO);
        if (trackingStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrackingStatusDTO result = trackingStatusService.save(trackingStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trackingStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tracking-statuses : get all the trackingStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trackingStatuses in body
     */
    @GetMapping("/tracking-statuses")
    public List<TrackingStatusDTO> getAllTrackingStatuses() {
        log.debug("REST request to get all TrackingStatuses");
        return trackingStatusService.findAll();
    }
    
    @GetMapping("/tracking-statuses-byvendor/{id}")
    public List<TrackingStatusDTO> getTrackingStatusByVendor(@PathVariable Long id) {
        log.debug("REST request to get PaymentModes By VendorId : {}", id);
        return trackingStatusService.findAllByVendor(id);
    }

    /**
     * GET  /tracking-statuses/:id : get the "id" trackingStatus.
     *
     * @param id the id of the trackingStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trackingStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tracking-statuses/{id}")
    public ResponseEntity<TrackingStatusDTO> getTrackingStatus(@PathVariable Long id) {
        log.debug("REST request to get TrackingStatus : {}", id);
        Optional<TrackingStatusDTO> trackingStatusDTO = trackingStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackingStatusDTO);
    }

    /**
     * DELETE  /tracking-statuses/:id : delete the "id" trackingStatus.
     *
     * @param id the id of the trackingStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tracking-statuses/{id}")
    public ResponseEntity<Void> deleteTrackingStatus(@PathVariable Long id) {
        log.debug("REST request to delete TrackingStatus : {}", id);
        trackingStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tracking-statuses?query=:query : search for the trackingStatus corresponding
     * to the query.
     *
     * @param query the query of the trackingStatus search
     * @return the result of the search
     */
    /*@GetMapping("/_search/tracking-statuses")
    public List<TrackingStatusDTO> searchTrackingStatuses(@RequestParam String query) {
        log.debug("REST request to search TrackingStatuses for query {}", query);
        return trackingStatusService.search(query);
    }*/

}
