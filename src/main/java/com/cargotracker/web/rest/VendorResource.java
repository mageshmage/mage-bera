package com.cargotracker.web.rest;
import com.cargotracker.domain.Vendor;
import com.cargotracker.service.VendorService;
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
 * REST controller for managing Vendor.
 */
@RestController
@RequestMapping("/api")
public class VendorResource {

    private final Logger log = LoggerFactory.getLogger(VendorResource.class);

    private static final String ENTITY_NAME = "vendor";

    private final VendorService vendorService;

    public VendorResource(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    /**
     * POST  /vendors : Create a new vendor.
     *
     * @param vendor the vendor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vendor, or with status 400 (Bad Request) if the vendor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vendors")
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody Vendor vendor) throws URISyntaxException {
        log.debug("REST request to save Vendor : {}", vendor);
        if (vendor.getId() != null) {
            throw new BadRequestAlertException("A new vendor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vendor result = vendorService.save(vendor);
        return ResponseEntity.created(new URI("/api/vendors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vendors : Updates an existing vendor.
     *
     * @param vendor the vendor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vendor,
     * or with status 400 (Bad Request) if the vendor is not valid,
     * or with status 500 (Internal Server Error) if the vendor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vendors")
    public ResponseEntity<Vendor> updateVendor(@Valid @RequestBody Vendor vendor) throws URISyntaxException {
        log.debug("REST request to update Vendor : {}", vendor);
        if (vendor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vendor result = vendorService.save(vendor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vendor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vendors : get all the vendors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vendors in body
     */
    @GetMapping("/vendors")
    public List<Vendor> getAllVendors() {
        log.debug("REST request to get all Vendors");
        return vendorService.findAll();
    }

    /**
     * GET  /vendors/:id : get the "id" vendor.
     *
     * @param id the id of the vendor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vendor, or with status 404 (Not Found)
     */
    @GetMapping("/vendors/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable Long id) {
        log.debug("REST request to get Vendor : {}", id);
        Optional<Vendor> vendor = vendorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendor);
    }

    /**
     * DELETE  /vendors/:id : delete the "id" vendor.
     *
     * @param id the id of the vendor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        log.debug("REST request to delete Vendor : {}", id);
        vendorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vendors?query=:query : search for the vendor corresponding
     * to the query.
     *
     * @param query the query of the vendor search
     * @return the result of the search
     */
    @GetMapping("/_search/vendors")
    public List<Vendor> searchVendors(@RequestParam String query) {
        log.debug("REST request to search Vendors for query {}", query);
        return vendorService.search(query);
    }

}
