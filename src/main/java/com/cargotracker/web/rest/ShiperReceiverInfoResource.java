package com.cargotracker.web.rest;
import com.cargotracker.service.ShiperReceiverInfoService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
import com.cargotracker.web.rest.util.PaginationUtil;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
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
     * @param shiperReceiverInfoDTO the shiperReceiverInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shiperReceiverInfoDTO, or with status 400 (Bad Request) if the shiperReceiverInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shiper-receiver-infos")
    public ResponseEntity<ShiperReceiverInfoDTO> createShiperReceiverInfo(@Valid @RequestBody ShiperReceiverInfoDTO shiperReceiverInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ShiperReceiverInfo : {}", shiperReceiverInfoDTO);
        if (shiperReceiverInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new shiperReceiverInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShiperReceiverInfoDTO result = shiperReceiverInfoService.save(shiperReceiverInfoDTO);
        return ResponseEntity.created(new URI("/api/shiper-receiver-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shiper-receiver-infos : Updates an existing shiperReceiverInfo.
     *
     * @param shiperReceiverInfoDTO the shiperReceiverInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shiperReceiverInfoDTO,
     * or with status 400 (Bad Request) if the shiperReceiverInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the shiperReceiverInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shiper-receiver-infos")
    public ResponseEntity<ShiperReceiverInfoDTO> updateShiperReceiverInfo(@Valid @RequestBody ShiperReceiverInfoDTO shiperReceiverInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ShiperReceiverInfo : {}", shiperReceiverInfoDTO);
        if (shiperReceiverInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShiperReceiverInfoDTO result = shiperReceiverInfoService.save(shiperReceiverInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shiperReceiverInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shiper-receiver-infos : get all the shiperReceiverInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shiperReceiverInfos in body
     */
    @GetMapping("/shiper-receiver-infos")
    public ResponseEntity<List<ShiperReceiverInfoDTO>> getAllShiperReceiverInfos(Pageable pageable) {
        log.debug("REST request to get a page of ShiperReceiverInfos");
        Page<ShiperReceiverInfoDTO> page = shiperReceiverInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shiper-receiver-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /shiper-receiver-infos/:id : get the "id" shiperReceiverInfo.
     *
     * @param id the id of the shiperReceiverInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shiperReceiverInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shiper-receiver-infos/{id}")
    public ResponseEntity<ShiperReceiverInfoDTO> getShiperReceiverInfo(@PathVariable Long id) {
        log.debug("REST request to get ShiperReceiverInfo : {}", id);
        Optional<ShiperReceiverInfoDTO> shiperReceiverInfoDTO = shiperReceiverInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shiperReceiverInfoDTO);
    }

    /**
     * DELETE  /shiper-receiver-infos/:id : delete the "id" shiperReceiverInfo.
     *
     * @param id the id of the shiperReceiverInfoDTO to delete
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
    public ResponseEntity<List<ShiperReceiverInfoDTO>> searchShiperReceiverInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShiperReceiverInfos for query {}", query);
        Page<ShiperReceiverInfoDTO> page = shiperReceiverInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/shiper-receiver-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
