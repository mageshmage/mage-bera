package com.cargotracker.web.rest;
import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.service.ShiperReceiverInfoService;
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
 * REST controller for managing ShiperReceiverInfo.
 */
@RestController
@RequestMapping("/api")
public class ShiperReceiverInfoResource {

    private final Logger log = LoggerFactory.getLogger(ShiperReceiverInfoResource.class);

    private static final String ENTITY_NAME = "shiperReceiverInfo";

    private final ShiperReceiverInfoService shiperReceiverInfoService;

    public ShiperReceiverInfoResource(ShiperReceiverInfoService shiperReceiverInfoService) {
        this.shiperReceiverInfoService = shiperReceiverInfoService;
    }

    /**
     * POST  /shiper-receiver-infos : Create a new shiperReceiverInfo.
     *
     * @param shiperReceiverInfo the shiperReceiverInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shiperReceiverInfo, or with status 400 (Bad Request) if the shiperReceiverInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shiper-receiver-infos")
    public ResponseEntity<ShiperReceiverInfo> createShiperReceiverInfo(@Valid @RequestBody ShiperReceiverInfo shiperReceiverInfo) throws URISyntaxException {
        log.debug("REST request to save ShiperReceiverInfo : {}", shiperReceiverInfo);
        if (shiperReceiverInfo.getId() != null) {
            throw new BadRequestAlertException("A new shiperReceiverInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShiperReceiverInfo result = shiperReceiverInfoService.save(shiperReceiverInfo);
        return ResponseEntity.created(new URI("/api/shiper-receiver-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shiper-receiver-infos : Updates an existing shiperReceiverInfo.
     *
     * @param shiperReceiverInfo the shiperReceiverInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shiperReceiverInfo,
     * or with status 400 (Bad Request) if the shiperReceiverInfo is not valid,
     * or with status 500 (Internal Server Error) if the shiperReceiverInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shiper-receiver-infos")
    public ResponseEntity<ShiperReceiverInfo> updateShiperReceiverInfo(@Valid @RequestBody ShiperReceiverInfo shiperReceiverInfo) throws URISyntaxException {
        log.debug("REST request to update ShiperReceiverInfo : {}", shiperReceiverInfo);
        if (shiperReceiverInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShiperReceiverInfo result = shiperReceiverInfoService.save(shiperReceiverInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shiperReceiverInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shiper-receiver-infos : get all the shiperReceiverInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shiperReceiverInfos in body
     */
    @GetMapping("/shiper-receiver-infos")
    public ResponseEntity<List<ShiperReceiverInfo>> getAllShiperReceiverInfos(Pageable pageable) {
        log.debug("REST request to get a page of ShiperReceiverInfos");
        Page<ShiperReceiverInfo> page = shiperReceiverInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shiper-receiver-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shiper-receiver-infos/:id : get the "id" shiperReceiverInfo.
     *
     * @param id the id of the shiperReceiverInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shiperReceiverInfo, or with status 404 (Not Found)
     */
    @GetMapping("/shiper-receiver-infos/{id}")
    public ResponseEntity<ShiperReceiverInfo> getShiperReceiverInfo(@PathVariable Long id) {
        log.debug("REST request to get ShiperReceiverInfo : {}", id);
        Optional<ShiperReceiverInfo> shiperReceiverInfo = shiperReceiverInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shiperReceiverInfo);
    }

    /**
     * DELETE  /shiper-receiver-infos/:id : delete the "id" shiperReceiverInfo.
     *
     * @param id the id of the shiperReceiverInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shiper-receiver-infos/{id}")
    public ResponseEntity<Void> deleteShiperReceiverInfo(@PathVariable Long id) {
        log.debug("REST request to delete ShiperReceiverInfo : {}", id);
        shiperReceiverInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/shiper-receiver-infos?query=:query : search for the shiperReceiverInfo corresponding
     * to the query.
     *
     * @param query the query of the shiperReceiverInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/shiper-receiver-infos")
    public ResponseEntity<List<ShiperReceiverInfo>> searchShiperReceiverInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShiperReceiverInfos for query {}", query);
        Page<ShiperReceiverInfo> page = shiperReceiverInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shiper-receiver-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
