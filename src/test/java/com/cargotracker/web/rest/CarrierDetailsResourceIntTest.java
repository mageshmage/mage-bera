package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.CarrierDetails;
import com.cargotracker.repository.CarrierDetailsRepository;
import com.cargotracker.repository.search.CarrierDetailsSearchRepository;
import com.cargotracker.service.CarrierDetailsService;
import com.cargotracker.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.cargotracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarrierDetailsResource REST controller.
 *
 * @see CarrierDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class CarrierDetailsResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private CarrierDetailsRepository carrierDetailsRepository;

    @Autowired
    private CarrierDetailsService carrierDetailsService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.CarrierDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CarrierDetailsSearchRepository mockCarrierDetailsSearchRepository;

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

    private MockMvc restCarrierDetailsMockMvc;

    private CarrierDetails carrierDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarrierDetailsResource carrierDetailsResource = new CarrierDetailsResource(carrierDetailsService);
        this.restCarrierDetailsMockMvc = MockMvcBuilders.standaloneSetup(carrierDetailsResource)
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
    public static CarrierDetails createEntity(EntityManager em) {
        CarrierDetails carrierDetails = new CarrierDetails()
            .value(DEFAULT_VALUE)
            .desc(DEFAULT_DESC);
        return carrierDetails;
    }

    @Before
    public void initTest() {
        carrierDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarrierDetails() throws Exception {
        int databaseSizeBeforeCreate = carrierDetailsRepository.findAll().size();

        // Create the CarrierDetails
        restCarrierDetailsMockMvc.perform(post("/api/carrier-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrierDetails)))
            .andExpect(status().isCreated());

        // Validate the CarrierDetails in the database
        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CarrierDetails testCarrierDetails = carrierDetailsList.get(carrierDetailsList.size() - 1);
        assertThat(testCarrierDetails.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCarrierDetails.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the CarrierDetails in Elasticsearch
        verify(mockCarrierDetailsSearchRepository, times(1)).save(testCarrierDetails);
    }

    @Test
    @Transactional
    public void createCarrierDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carrierDetailsRepository.findAll().size();

        // Create the CarrierDetails with an existing ID
        carrierDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierDetailsMockMvc.perform(post("/api/carrier-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrierDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierDetails in the database
        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CarrierDetails in Elasticsearch
        verify(mockCarrierDetailsSearchRepository, times(0)).save(carrierDetails);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierDetailsRepository.findAll().size();
        // set the field null
        carrierDetails.setValue(null);

        // Create the CarrierDetails, which fails.

        restCarrierDetailsMockMvc.perform(post("/api/carrier-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrierDetails)))
            .andExpect(status().isBadRequest());

        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarrierDetails() throws Exception {
        // Initialize the database
        carrierDetailsRepository.saveAndFlush(carrierDetails);

        // Get all the carrierDetailsList
        restCarrierDetailsMockMvc.perform(get("/api/carrier-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getCarrierDetails() throws Exception {
        // Initialize the database
        carrierDetailsRepository.saveAndFlush(carrierDetails);

        // Get the carrierDetails
        restCarrierDetailsMockMvc.perform(get("/api/carrier-details/{id}", carrierDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carrierDetails.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrierDetails() throws Exception {
        // Get the carrierDetails
        restCarrierDetailsMockMvc.perform(get("/api/carrier-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrierDetails() throws Exception {
        // Initialize the database
        carrierDetailsService.save(carrierDetails);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCarrierDetailsSearchRepository);

        int databaseSizeBeforeUpdate = carrierDetailsRepository.findAll().size();

        // Update the carrierDetails
        CarrierDetails updatedCarrierDetails = carrierDetailsRepository.findById(carrierDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCarrierDetails are not directly saved in db
        em.detach(updatedCarrierDetails);
        updatedCarrierDetails
            .value(UPDATED_VALUE)
            .desc(UPDATED_DESC);

        restCarrierDetailsMockMvc.perform(put("/api/carrier-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarrierDetails)))
            .andExpect(status().isOk());

        // Validate the CarrierDetails in the database
        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeUpdate);
        CarrierDetails testCarrierDetails = carrierDetailsList.get(carrierDetailsList.size() - 1);
        assertThat(testCarrierDetails.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCarrierDetails.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the CarrierDetails in Elasticsearch
        verify(mockCarrierDetailsSearchRepository, times(1)).save(testCarrierDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCarrierDetails() throws Exception {
        int databaseSizeBeforeUpdate = carrierDetailsRepository.findAll().size();

        // Create the CarrierDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierDetailsMockMvc.perform(put("/api/carrier-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carrierDetails)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierDetails in the database
        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CarrierDetails in Elasticsearch
        verify(mockCarrierDetailsSearchRepository, times(0)).save(carrierDetails);
    }

    @Test
    @Transactional
    public void deleteCarrierDetails() throws Exception {
        // Initialize the database
        carrierDetailsService.save(carrierDetails);

        int databaseSizeBeforeDelete = carrierDetailsRepository.findAll().size();

        // Delete the carrierDetails
        restCarrierDetailsMockMvc.perform(delete("/api/carrier-details/{id}", carrierDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarrierDetails> carrierDetailsList = carrierDetailsRepository.findAll();
        assertThat(carrierDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CarrierDetails in Elasticsearch
        verify(mockCarrierDetailsSearchRepository, times(1)).deleteById(carrierDetails.getId());
    }

    @Test
    @Transactional
    public void searchCarrierDetails() throws Exception {
        // Initialize the database
        carrierDetailsService.save(carrierDetails);
        when(mockCarrierDetailsSearchRepository.search(queryStringQuery("id:" + carrierDetails.getId())))
            .thenReturn(Collections.singletonList(carrierDetails));
        // Search the carrierDetails
        restCarrierDetailsMockMvc.perform(get("/api/_search/carrier-details?query=id:" + carrierDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierDetails.class);
        CarrierDetails carrierDetails1 = new CarrierDetails();
        carrierDetails1.setId(1L);
        CarrierDetails carrierDetails2 = new CarrierDetails();
        carrierDetails2.setId(carrierDetails1.getId());
        assertThat(carrierDetails1).isEqualTo(carrierDetails2);
        carrierDetails2.setId(2L);
        assertThat(carrierDetails1).isNotEqualTo(carrierDetails2);
        carrierDetails1.setId(null);
        assertThat(carrierDetails1).isNotEqualTo(carrierDetails2);
    }
}
