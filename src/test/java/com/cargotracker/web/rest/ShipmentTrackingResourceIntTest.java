package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShipmentTracking;
import com.cargotracker.repository.ShipmentTrackingRepository;
import com.cargotracker.repository.search.ShipmentTrackingSearchRepository;
import com.cargotracker.service.ShipmentTrackingService;
import com.cargotracker.service.dto.ShipmentTrackingDTO;
import com.cargotracker.service.mapper.ShipmentTrackingMapper;
import com.cargotracker.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.cargotracker.web.rest.TestUtil.sameInstant;
import static com.cargotracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShipmentTrackingResource REST controller.
 *
 * @see ShipmentTrackingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShipmentTrackingResourceIntTest {

    private static final ZonedDateTime DEFAULT_TRACKING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TRACKING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ShipmentTrackingRepository shipmentTrackingRepository;

    @Autowired
    private ShipmentTrackingMapper shipmentTrackingMapper;

    @Autowired
    private ShipmentTrackingService shipmentTrackingService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShipmentTrackingSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipmentTrackingSearchRepository mockShipmentTrackingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restShipmentTrackingMockMvc;

    private ShipmentTracking shipmentTracking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentTrackingResource shipmentTrackingResource = new ShipmentTrackingResource(shipmentTrackingService);
        this.restShipmentTrackingMockMvc = MockMvcBuilders.standaloneSetup(shipmentTrackingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentTracking createEntity(EntityManager em) {
        ShipmentTracking shipmentTracking = new ShipmentTracking()
            .trackingDate(DEFAULT_TRACKING_DATE)
            .place(DEFAULT_PLACE)
            .status(DEFAULT_STATUS);
        return shipmentTracking;
    }

    @Before
    public void initTest() {
        shipmentTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentTracking() throws Exception {
        int databaseSizeBeforeCreate = shipmentTrackingRepository.findAll().size();

        // Create the ShipmentTracking
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);
        restShipmentTrackingMockMvc.perform(post("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentTracking in the database
        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentTracking testShipmentTracking = shipmentTrackingList.get(shipmentTrackingList.size() - 1);
        assertThat(testShipmentTracking.getTrackingDate()).isEqualTo(DEFAULT_TRACKING_DATE);
        assertThat(testShipmentTracking.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testShipmentTracking.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ShipmentTracking in Elasticsearch
        verify(mockShipmentTrackingSearchRepository, times(1)).save(testShipmentTracking);
    }

    @Test
    @Transactional
    public void createShipmentTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentTrackingRepository.findAll().size();

        // Create the ShipmentTracking with an existing ID
        shipmentTracking.setId(1L);
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentTrackingMockMvc.perform(post("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentTracking in the database
        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentTracking in Elasticsearch
        verify(mockShipmentTrackingSearchRepository, times(0)).save(shipmentTracking);
    }

    @Test
    @Transactional
    public void checkTrackingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentTrackingRepository.findAll().size();
        // set the field null
        shipmentTracking.setTrackingDate(null);

        // Create the ShipmentTracking, which fails.
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);

        restShipmentTrackingMockMvc.perform(post("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentTrackingRepository.findAll().size();
        // set the field null
        shipmentTracking.setPlace(null);

        // Create the ShipmentTracking, which fails.
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);

        restShipmentTrackingMockMvc.perform(post("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentTrackingRepository.findAll().size();
        // set the field null
        shipmentTracking.setStatus(null);

        // Create the ShipmentTracking, which fails.
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);

        restShipmentTrackingMockMvc.perform(post("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShipmentTrackings() throws Exception {
        // Initialize the database
        shipmentTrackingRepository.saveAndFlush(shipmentTracking);

        // Get all the shipmentTrackingList
        restShipmentTrackingMockMvc.perform(get("/api/shipment-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingDate").value(hasItem(sameInstant(DEFAULT_TRACKING_DATE))))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getShipmentTracking() throws Exception {
        // Initialize the database
        shipmentTrackingRepository.saveAndFlush(shipmentTracking);

        // Get the shipmentTracking
        restShipmentTrackingMockMvc.perform(get("/api/shipment-trackings/{id}", shipmentTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentTracking.getId().intValue()))
            .andExpect(jsonPath("$.trackingDate").value(sameInstant(DEFAULT_TRACKING_DATE)))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentTracking() throws Exception {
        // Get the shipmentTracking
        restShipmentTrackingMockMvc.perform(get("/api/shipment-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentTracking() throws Exception {
        // Initialize the database
        shipmentTrackingRepository.saveAndFlush(shipmentTracking);

        int databaseSizeBeforeUpdate = shipmentTrackingRepository.findAll().size();

        // Update the shipmentTracking
        ShipmentTracking updatedShipmentTracking = shipmentTrackingRepository.findById(shipmentTracking.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentTracking are not directly saved in db
        em.detach(updatedShipmentTracking);
        updatedShipmentTracking
            .trackingDate(UPDATED_TRACKING_DATE)
            .place(UPDATED_PLACE)
            .status(UPDATED_STATUS);
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(updatedShipmentTracking);

        restShipmentTrackingMockMvc.perform(put("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentTracking in the database
        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeUpdate);
        ShipmentTracking testShipmentTracking = shipmentTrackingList.get(shipmentTrackingList.size() - 1);
        assertThat(testShipmentTracking.getTrackingDate()).isEqualTo(UPDATED_TRACKING_DATE);
        assertThat(testShipmentTracking.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testShipmentTracking.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ShipmentTracking in Elasticsearch
        verify(mockShipmentTrackingSearchRepository, times(1)).save(testShipmentTracking);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentTracking() throws Exception {
        int databaseSizeBeforeUpdate = shipmentTrackingRepository.findAll().size();

        // Create the ShipmentTracking
        ShipmentTrackingDTO shipmentTrackingDTO = shipmentTrackingMapper.toDto(shipmentTracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentTrackingMockMvc.perform(put("/api/shipment-trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentTracking in the database
        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentTracking in Elasticsearch
        verify(mockShipmentTrackingSearchRepository, times(0)).save(shipmentTracking);
    }

    @Test
    @Transactional
    public void deleteShipmentTracking() throws Exception {
        // Initialize the database
        shipmentTrackingRepository.saveAndFlush(shipmentTracking);

        int databaseSizeBeforeDelete = shipmentTrackingRepository.findAll().size();

        // Delete the shipmentTracking
        restShipmentTrackingMockMvc.perform(delete("/api/shipment-trackings/{id}", shipmentTracking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentTracking> shipmentTrackingList = shipmentTrackingRepository.findAll();
        assertThat(shipmentTrackingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentTracking in Elasticsearch
        verify(mockShipmentTrackingSearchRepository, times(1)).deleteById(shipmentTracking.getId());
    }

    @Test
    @Transactional
    public void searchShipmentTracking() throws Exception {
        // Initialize the database
        shipmentTrackingRepository.saveAndFlush(shipmentTracking);
        when(mockShipmentTrackingSearchRepository.search(queryStringQuery("id:" + shipmentTracking.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(shipmentTracking), PageRequest.of(0, 1), 1));
        // Search the shipmentTracking
        restShipmentTrackingMockMvc.perform(get("/api/_search/shipment-trackings?query=id:" + shipmentTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingDate").value(hasItem(sameInstant(DEFAULT_TRACKING_DATE))))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentTracking.class);
        ShipmentTracking shipmentTracking1 = new ShipmentTracking();
        shipmentTracking1.setId(1L);
        ShipmentTracking shipmentTracking2 = new ShipmentTracking();
        shipmentTracking2.setId(shipmentTracking1.getId());
        assertThat(shipmentTracking1).isEqualTo(shipmentTracking2);
        shipmentTracking2.setId(2L);
        assertThat(shipmentTracking1).isNotEqualTo(shipmentTracking2);
        shipmentTracking1.setId(null);
        assertThat(shipmentTracking1).isNotEqualTo(shipmentTracking2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentTrackingDTO.class);
        ShipmentTrackingDTO shipmentTrackingDTO1 = new ShipmentTrackingDTO();
        shipmentTrackingDTO1.setId(1L);
        ShipmentTrackingDTO shipmentTrackingDTO2 = new ShipmentTrackingDTO();
        assertThat(shipmentTrackingDTO1).isNotEqualTo(shipmentTrackingDTO2);
        shipmentTrackingDTO2.setId(shipmentTrackingDTO1.getId());
        assertThat(shipmentTrackingDTO1).isEqualTo(shipmentTrackingDTO2);
        shipmentTrackingDTO2.setId(2L);
        assertThat(shipmentTrackingDTO1).isNotEqualTo(shipmentTrackingDTO2);
        shipmentTrackingDTO1.setId(null);
        assertThat(shipmentTrackingDTO1).isNotEqualTo(shipmentTrackingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentTrackingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentTrackingMapper.fromId(null)).isNull();
    }
}
