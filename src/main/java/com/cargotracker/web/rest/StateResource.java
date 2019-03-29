package com.cargotracker.web.rest;
import com.cargotracker.domain.State;
import com.cargotracker.service.StateService;
import com.cargotracker.web.rest.errors.BadRequestAlertException;
import com.cargotracker.web.rest.util.HeaderUtil;
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
 * REST controller for managing State.
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    private static final String ENTITY_NAME = "state";

    private final StateService stateService;

    public StateResource(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * POST  /states : Create a new state.
     *
     * @param state the state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new state, or with status 400 (Bad Request) if the state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/states")
    public ResponseEntity<State> createState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to save State : {}", state);
        if (state.getId() != null) {
            throw new BadRequestAlertException("A new state cannot already have an ID", ENTITY_NAME, "idexists");
        }
        State result = stateService.save(state);
        return ResponseEntity.created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /states : Updates an existing state.
     *
     * @param state the state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated state,
     * or with status 400 (Bad Request) if the state is not valid,
     * or with status 500 (Internal Server Error) if the state couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/states")
    public ResponseEntity<State> updateState(@RequestBody State state) throws URISyntaxException {
        log.debug("REST request to update State : {}", state);
        if (state.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        State result = stateService.save(state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/states")
    public List<State> getAllStates() {
        log.debug("REST request to get all States");
        return stateService.findAll();
    }

    /**
     * GET  /states/:id : get the "id" state.
     *
     * @param id the id of the state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the state, or with status 404 (Not Found)
     */
    @GetMapping("/states/{id}")
    public ResponseEntity<State> getState(@PathVariable Long id) {
        log.debug("REST request to get State : {}", id);
        Optional<State> state = stateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(state);
    }

    /**
     * DELETE  /states/:id : delete the "id" state.
     *
     * @param id the id of the state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/states/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        log.debug("REST request to delete State : {}", id);
        stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/states?query=:query : search for the state corresponding
     * to the query.
     *
     * @param query the query of the state search
     * @return the result of the search
     */
    @GetMapping("/_search/states")
    public List<State> searchStates(@RequestParam String query) {
        log.debug("REST request to search States for query {}", query);
        return stateService.search(query);
    }

}
