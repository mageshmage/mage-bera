package com.cargotracker.web.rest;
import com.cargotracker.domain.PaymentMode;
import com.cargotracker.service.PaymentModeService;
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
 * REST controller for managing PaymentMode.
 */
@RestController
@RequestMapping("/api")
public class PaymentModeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentModeResource.class);

    private static final String ENTITY_NAME = "paymentMode";

    private final PaymentModeService paymentModeService;

    public PaymentModeResource(PaymentModeService paymentModeService) {
        this.paymentModeService = paymentModeService;
    }

    /**
     * POST  /payment-modes : Create a new paymentMode.
     *
     * @param paymentMode the paymentMode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentMode, or with status 400 (Bad Request) if the paymentMode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-modes")
    public ResponseEntity<PaymentMode> createPaymentMode(@Valid @RequestBody PaymentMode paymentMode) throws URISyntaxException {
        log.debug("REST request to save PaymentMode : {}", paymentMode);
        if (paymentMode.getId() != null) {
            throw new BadRequestAlertException("A new paymentMode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentMode result = paymentModeService.save(paymentMode);
        return ResponseEntity.created(new URI("/api/payment-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-modes : Updates an existing paymentMode.
     *
     * @param paymentMode the paymentMode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentMode,
     * or with status 400 (Bad Request) if the paymentMode is not valid,
     * or with status 500 (Internal Server Error) if the paymentMode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-modes")
    public ResponseEntity<PaymentMode> updatePaymentMode(@Valid @RequestBody PaymentMode paymentMode) throws URISyntaxException {
        log.debug("REST request to update PaymentMode : {}", paymentMode);
        if (paymentMode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentMode result = paymentModeService.save(paymentMode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentMode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-modes : get all the paymentModes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentModes in body
     */
    @GetMapping("/payment-modes")
    public List<PaymentMode> getAllPaymentModes() {
        log.debug("REST request to get all PaymentModes");
        return paymentModeService.findAll();
    }

    /**
     * GET  /payment-modes/:id : get the "id" paymentMode.
     *
     * @param id the id of the paymentMode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentMode, or with status 404 (Not Found)
     */
    @GetMapping("/payment-modes/{id}")
    public ResponseEntity<PaymentMode> getPaymentMode(@PathVariable Long id) {
        log.debug("REST request to get PaymentMode : {}", id);
        Optional<PaymentMode> paymentMode = paymentModeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentMode);
    }

    /**
     * DELETE  /payment-modes/:id : delete the "id" paymentMode.
     *
     * @param id the id of the paymentMode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-modes/{id}")
    public ResponseEntity<Void> deletePaymentMode(@PathVariable Long id) {
        log.debug("REST request to delete PaymentMode : {}", id);
        paymentModeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/payment-modes?query=:query : search for the paymentMode corresponding
     * to the query.
     *
     * @param query the query of the paymentMode search
     * @return the result of the search
     */
    @GetMapping("/_search/payment-modes")
    public List<PaymentMode> searchPaymentModes(@RequestParam String query) {
        log.debug("REST request to search PaymentModes for query {}", query);
        return paymentModeService.search(query);
    }

}
