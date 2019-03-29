package com.cargotracker.web.rest;
import com.cargotracker.domain.CarrierDetails;
import com.cargotracker.service.CarrierDetailsService;
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
 * REST controller for managing CarrierDetails.
 */
@RestController
@RequestMapping("/api")
public class CarrierDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CarrierDetailsResource.class);

    private static final String ENTITY_NAME = "carrierDetails";

    private final CarrierDetailsService carrierDetailsService;

    public CarrierDetailsResource(CarrierDetailsService carrierDetailsService) {
        this.carrierDetailsService = carrierDetailsService;
    }

    /**
     * POST  /carrier-details : Create a new carrierDetails.
     *
     * @param carrierDetails the carrierDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrierDetails, or with status 400 (Bad Request) if the carrierDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carrier-details")
    public ResponseEntity<CarrierDetails> createCarrierDetails(@Valid @RequestBody CarrierDetails carrierDetails) throws URISyntaxException {
        log.debug("REST request to save CarrierDetails : {}", carrierDetails);
        if (carrierDetails.getId() != null) {
            throw new BadRequestAlertException("A new carrierDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarrierDetails result = carrierDetailsService.save(carrierDetails);
        return ResponseEntity.created(new URI("/api/carrier-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carrier-details : Updates an existing carrierDetails.
     *
     * @param carrierDetails the carrierDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrierDetails,
     * or with status 400 (Bad Request) if the carrierDetails is not valid,
     * or with status 500 (Internal Server Error) if the carrierDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carrier-details")
    public ResponseEntity<CarrierDetails> updateCarrierDetails(@Valid @RequestBody CarrierDetails carrierDetails) throws URISyntaxException {
        log.debug("REST request to update CarrierDetails : {}", carrierDetails);
        if (carrierDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarrierDetails result = carrierDetailsService.save(carrierDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carrierDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carrier-details : get all the carrierDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carrierDetails in body
     */
    @GetMapping("/carrier-details")
    public List<CarrierDetails> getAllCarrierDetails() {
        log.debug("REST request to get all CarrierDetails");
        return carrierDetailsService.findAll();
    }

    /**
     * GET  /carrier-details/:id : get the "id" carrierDetails.
     *
     * @param id the id of the carrierDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrierDetails, or with status 404 (Not Found)
     */
    @GetMapping("/carrier-details/{id}")
    public ResponseEntity<CarrierDetails> getCarrierDetails(@PathVariable Long id) {
        log.debug("REST request to get CarrierDetails : {}", id);
        Optional<CarrierDetails> carrierDetails = carrierDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierDetails);
    }

    /**
     * DELETE  /carrier-details/:id : delete the "id" carrierDetails.
     *
     * @param id the id of the carrierDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carrier-details/{id}")
    public ResponseEntity<Void> deleteCarrierDetails(@PathVariable Long id) {
        log.debug("REST request to delete CarrierDetails : {}", id);
        carrierDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/carrier-details?query=:query : search for the carrierDetails corresponding
     * to the query.
     *
     * @param query the query of the carrierDetails search
     * @return the result of the search
     */
    @GetMapping("/_search/carrier-details")
    public List<CarrierDetails> searchCarrierDetails(@RequestParam String query) {
        log.debug("REST request to search CarrierDetails for query {}", query);
        return carrierDetailsService.search(query);
    }

}
