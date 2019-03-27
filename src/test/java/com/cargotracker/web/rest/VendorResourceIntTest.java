package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.Vendor;
import com.cargotracker.repository.VendorRepository;
import com.cargotracker.repository.search.VendorSearchRepository;
import com.cargotracker.service.VendorService;
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
 * Test class for the VendorResource REST controller.
 *
 * @see VendorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class VendorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorService vendorService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.VendorSearchRepositoryMockConfiguration
     */
    @Autowired
    private VendorSearchRepository mockVendorSearchRepository;

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

    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorResource vendorResource = new VendorResource(vendorService);
        this.restVendorMockMvc = MockMvcBuilders.standaloneSetup(vendorResource)
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
    public static Vendor createEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .name(DEFAULT_NAME)
            .pan(DEFAULT_PAN)
            .gstin(DEFAULT_GSTIN);
        return vendor;
    }

    @Before
    public void initTest() {
        vendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVendor.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testVendor.getGstin()).isEqualTo(DEFAULT_GSTIN);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).save(testVendor);
    }

    @Test
    @Transactional
    public void createVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor with an existing ID
        vendor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(0)).save(vendor);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setName(null);

        // Create the Vendor, which fails.

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPanIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setPan(null);

        // Create the Vendor, which fails.

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGstinIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setGstin(null);

        // Create the Vendor, which fails.

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN.toString())))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN.toString())));
    }
    
    @Test
    @Transactional
    public void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN.toString()))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendor() throws Exception {
        // Initialize the database
        vendorService.save(vendor);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockVendorSearchRepository);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = vendorRepository.findById(vendor.getId()).get();
        // Disconnect from session so that the updates on updatedVendor are not directly saved in db
        em.detach(updatedVendor);
        updatedVendor
            .name(UPDATED_NAME)
            .pan(UPDATED_PAN)
            .gstin(UPDATED_GSTIN);

        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVendor)))
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVendor.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testVendor.getGstin()).isEqualTo(UPDATED_GSTIN);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).save(testVendor);
    }

    @Test
    @Transactional
    public void updateNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Create the Vendor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendor)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(0)).save(vendor);
    }

    @Test
    @Transactional
    public void deleteVendor() throws Exception {
        // Initialize the database
        vendorService.save(vendor);

        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Delete the vendor
        restVendorMockMvc.perform(delete("/api/vendors/{id}", vendor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).deleteById(vendor.getId());
    }

    @Test
    @Transactional
    public void searchVendor() throws Exception {
        // Initialize the database
        vendorService.save(vendor);
        when(mockVendorSearchRepository.search(queryStringQuery("id:" + vendor.getId())))
            .thenReturn(Collections.singletonList(vendor));
        // Search the vendor
        restVendorMockMvc.perform(get("/api/_search/vendors?query=id:" + vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vendor.class);
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        Vendor vendor2 = new Vendor();
        vendor2.setId(vendor1.getId());
        assertThat(vendor1).isEqualTo(vendor2);
        vendor2.setId(2L);
        assertThat(vendor1).isNotEqualTo(vendor2);
        vendor1.setId(null);
        assertThat(vendor1).isNotEqualTo(vendor2);
    }
}
