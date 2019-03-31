package com.cargotracker.web.rest;
import com.cargotracker.service.CarrierDetailsService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.service.dto.CarrierDetailsDTO;
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
     * @param carrierDetailsDTO the carrierDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrierDetailsDTO, or with status 400 (Bad Request) if the carrierDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carrier-details")
    public ResponseEntity<CarrierDetailsDTO> createCarrierDetails(@Valid @RequestBody CarrierDetailsDTO carrierDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CarrierDetails : {}", carrierDetailsDTO);
        if (carrierDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrierDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarrierDetailsDTO result = carrierDetailsService.save(carrierDetailsDTO);
        return ResponseEntity.created(new URI("/api/carrier-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carrier-details : Updates an existing carrierDetails.
     *
     * @param carrierDetailsDTO the carrierDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrierDetailsDTO,
     * or with status 400 (Bad Request) if the carrierDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the carrierDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carrier-details")
    public ResponseEntity<CarrierDetailsDTO> updateCarrierDetails(@Valid @RequestBody CarrierDetailsDTO carrierDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update CarrierDetails : {}", carrierDetailsDTO);
        if (carrierDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarrierDetailsDTO result = carrierDetailsService.save(carrierDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carrierDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carrier-details : get all the carrierDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carrierDetails in body
     */
    @GetMapping("/carrier-details")
    public List<CarrierDetailsDTO> getAllCarrierDetails() {
        log.debug("REST request to get all CarrierDetails");
        return carrierDetailsService.findAll();
    }

    /**
     * GET  /carrier-details/:id : get the "id" carrierDetails.
     *
     * @param id the id of the carrierDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrierDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carrier-details/{id}")
    public ResponseEntity<CarrierDetailsDTO> getCarrierDetails(@PathVariable Long id) {
        log.debug("REST request to get CarrierDetails : {}", id);
        Optional<CarrierDetailsDTO> carrierDetailsDTO = carrierDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierDetailsDTO);
    }

    /**
     * DELETE  /carrier-details/:id : delete the "id" carrierDetails.
     *
     * @param id the id of the carrierDetailsDTO to delete
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
    public List<CarrierDetailsDTO> searchCarrierDetails(@RequestParam String query) {
        log.debug("REST request to search CarrierDetails for query {}", query);
        return carrierDetailsService.search(query);
    }

}
