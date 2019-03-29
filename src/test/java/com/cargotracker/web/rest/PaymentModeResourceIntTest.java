package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.PaymentMode;
import com.cargotracker.repository.PaymentModeRepository;
import com.cargotracker.repository.search.PaymentModeSearchRepository;
import com.cargotracker.service.PaymentModeService;
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
 * Test class for the PaymentModeResource REST controller.
 *
 * @see PaymentModeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class PaymentModeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private PaymentModeRepository paymentModeRepository;

    @Autowired
    private PaymentModeService paymentModeService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.PaymentModeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentModeSearchRepository mockPaymentModeSearchRepository;

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

    private MockMvc restPaymentModeMockMvc;

    private PaymentMode paymentMode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentModeResource paymentModeResource = new PaymentModeResource(paymentModeService);
        this.restPaymentModeMockMvc = MockMvcBuilders.standaloneSetup(paymentModeResource)
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
    public static PaymentMode createEntity(EntityManager em) {
        PaymentMode paymentMode = new PaymentMode()
            .value(DEFAULT_VALUE)
            .desc(DEFAULT_DESC);
        return paymentMode;
    }

    @Before
    public void initTest() {
        paymentMode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentMode() throws Exception {
        int databaseSizeBeforeCreate = paymentModeRepository.findAll().size();

        // Create the PaymentMode
        restPaymentModeMockMvc.perform(post("/api/payment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMode)))
            .andExpect(status().isCreated());

        // Validate the PaymentMode in the database
        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMode testPaymentMode = paymentModeList.get(paymentModeList.size() - 1);
        assertThat(testPaymentMode.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPaymentMode.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the PaymentMode in Elasticsearch
        verify(mockPaymentModeSearchRepository, times(1)).save(testPaymentMode);
    }

    @Test
    @Transactional
    public void createPaymentModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentModeRepository.findAll().size();

        // Create the PaymentMode with an existing ID
        paymentMode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentModeMockMvc.perform(post("/api/payment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMode)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMode in the database
        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentMode in Elasticsearch
        verify(mockPaymentModeSearchRepository, times(0)).save(paymentMode);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentModeRepository.findAll().size();
        // set the field null
        paymentMode.setValue(null);

        // Create the PaymentMode, which fails.

        restPaymentModeMockMvc.perform(post("/api/payment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMode)))
            .andExpect(status().isBadRequest());

        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentModes() throws Exception {
        // Initialize the database
        paymentModeRepository.saveAndFlush(paymentMode);

        // Get all the paymentModeList
        restPaymentModeMockMvc.perform(get("/api/payment-modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentMode() throws Exception {
        // Initialize the database
        paymentModeRepository.saveAndFlush(paymentMode);

        // Get the paymentMode
        restPaymentModeMockMvc.perform(get("/api/payment-modes/{id}", paymentMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMode.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentMode() throws Exception {
        // Get the paymentMode
        restPaymentModeMockMvc.perform(get("/api/payment-modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentMode() throws Exception {
        // Initialize the database
        paymentModeService.save(paymentMode);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPaymentModeSearchRepository);

        int databaseSizeBeforeUpdate = paymentModeRepository.findAll().size();

        // Update the paymentMode
        PaymentMode updatedPaymentMode = paymentModeRepository.findById(paymentMode.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMode are not directly saved in db
        em.detach(updatedPaymentMode);
        updatedPaymentMode
            .value(UPDATED_VALUE)
            .desc(UPDATED_DESC);

        restPaymentModeMockMvc.perform(put("/api/payment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentMode)))
            .andExpect(status().isOk());

        // Validate the PaymentMode in the database
        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeUpdate);
        PaymentMode testPaymentMode = paymentModeList.get(paymentModeList.size() - 1);
        assertThat(testPaymentMode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPaymentMode.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the PaymentMode in Elasticsearch
        verify(mockPaymentModeSearchRepository, times(1)).save(testPaymentMode);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentMode() throws Exception {
        int databaseSizeBeforeUpdate = paymentModeRepository.findAll().size();

        // Create the PaymentMode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentModeMockMvc.perform(put("/api/payment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMode)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMode in the database
        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentMode in Elasticsearch
        verify(mockPaymentModeSearchRepository, times(0)).save(paymentMode);
    }

    @Test
    @Transactional
    public void deletePaymentMode() throws Exception {
        // Initialize the database
        paymentModeService.save(paymentMode);

        int databaseSizeBeforeDelete = paymentModeRepository.findAll().size();

        // Delete the paymentMode
        restPaymentModeMockMvc.perform(delete("/api/payment-modes/{id}", paymentMode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentMode> paymentModeList = paymentModeRepository.findAll();
        assertThat(paymentModeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentMode in Elasticsearch
        verify(mockPaymentModeSearchRepository, times(1)).deleteById(paymentMode.getId());
    }

    @Test
    @Transactional
    public void searchPaymentMode() throws Exception {
        // Initialize the database
        paymentModeService.save(paymentMode);
        when(mockPaymentModeSearchRepository.search(queryStringQuery("id:" + paymentMode.getId())))
            .thenReturn(Collections.singletonList(paymentMode));
        // Search the paymentMode
        restPaymentModeMockMvc.perform(get("/api/_search/payment-modes?query=id:" + paymentMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMode.class);
        PaymentMode paymentMode1 = new PaymentMode();
        paymentMode1.setId(1L);
        PaymentMode paymentMode2 = new PaymentMode();
        paymentMode2.setId(paymentMode1.getId());
        assertThat(paymentMode1).isEqualTo(paymentMode2);
        paymentMode2.setId(2L);
        assertThat(paymentMode1).isNotEqualTo(paymentMode2);
        paymentMode1.setId(null);
        assertThat(paymentMode1).isNotEqualTo(paymentMode2);
    }
}
