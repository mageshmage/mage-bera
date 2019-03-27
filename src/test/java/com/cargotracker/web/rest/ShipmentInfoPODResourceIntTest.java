package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShipmentInfoPOD;
import com.cargotracker.repository.ShipmentInfoPODRepository;
import com.cargotracker.repository.search.ShipmentInfoPODSearchRepository;
import com.cargotracker.service.ShipmentInfoPODService;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the ShipmentInfoPODResource REST controller.
 *
 * @see ShipmentInfoPODResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShipmentInfoPODResourceIntTest {

    private static final byte[] DEFAULT_POD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POD_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ShipmentInfoPODRepository shipmentInfoPODRepository;

    @Autowired
    private ShipmentInfoPODService shipmentInfoPODService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShipmentInfoPODSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipmentInfoPODSearchRepository mockShipmentInfoPODSearchRepository;

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

    private MockMvc restShipmentInfoPODMockMvc;

    private ShipmentInfoPOD shipmentInfoPOD;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentInfoPODResource shipmentInfoPODResource = new ShipmentInfoPODResource(shipmentInfoPODService);
        this.restShipmentInfoPODMockMvc = MockMvcBuilders.standaloneSetup(shipmentInfoPODResource)
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
    public static ShipmentInfoPOD createEntity(EntityManager em) {
        ShipmentInfoPOD shipmentInfoPOD = new ShipmentInfoPOD()
            .pod(DEFAULT_POD)
            .podContentType(DEFAULT_POD_CONTENT_TYPE)
            .comments(DEFAULT_COMMENTS);
        return shipmentInfoPOD;
    }

    @Before
    public void initTest() {
        shipmentInfoPOD = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentInfoPOD() throws Exception {
        int databaseSizeBeforeCreate = shipmentInfoPODRepository.findAll().size();

        // Create the ShipmentInfoPOD
        restShipmentInfoPODMockMvc.perform(post("/api/shipment-info-pods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfoPOD)))
            .andExpect(status().isCreated());

        // Validate the ShipmentInfoPOD in the database
        List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository.findAll();
        assertThat(shipmentInfoPODList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentInfoPOD testShipmentInfoPOD = shipmentInfoPODList.get(shipmentInfoPODList.size() - 1);
        assertThat(testShipmentInfoPOD.getPod()).isEqualTo(DEFAULT_POD);
        assertThat(testShipmentInfoPOD.getPodContentType()).isEqualTo(DEFAULT_POD_CONTENT_TYPE);
        assertThat(testShipmentInfoPOD.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the ShipmentInfoPOD in Elasticsearch
        verify(mockShipmentInfoPODSearchRepository, times(1)).save(testShipmentInfoPOD);
    }

    @Test
    @Transactional
    public void createShipmentInfoPODWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentInfoPODRepository.findAll().size();

        // Create the ShipmentInfoPOD with an existing ID
        shipmentInfoPOD.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentInfoPODMockMvc.perform(post("/api/shipment-info-pods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfoPOD)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentInfoPOD in the database
        List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository.findAll();
        assertThat(shipmentInfoPODList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentInfoPOD in Elasticsearch
        verify(mockShipmentInfoPODSearchRepository, times(0)).save(shipmentInfoPOD);
    }

    @Test
    @Transactional
    public void getAllShipmentInfoPODS() throws Exception {
        // Initialize the database
        shipmentInfoPODRepository.saveAndFlush(shipmentInfoPOD);

        // Get all the shipmentInfoPODList
        restShipmentInfoPODMockMvc.perform(get("/api/shipment-info-pods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentInfoPOD.getId().intValue())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }
    
    @Test
    @Transactional
    public void getShipmentInfoPOD() throws Exception {
        // Initialize the database
        shipmentInfoPODRepository.saveAndFlush(shipmentInfoPOD);

        // Get the shipmentInfoPOD
        restShipmentInfoPODMockMvc.perform(get("/api/shipment-info-pods/{id}", shipmentInfoPOD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentInfoPOD.getId().intValue()))
            .andExpect(jsonPath("$.podContentType").value(DEFAULT_POD_CONTENT_TYPE))
            .andExpect(jsonPath("$.pod").value(Base64Utils.encodeToString(DEFAULT_POD)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentInfoPOD() throws Exception {
        // Get the shipmentInfoPOD
        restShipmentInfoPODMockMvc.perform(get("/api/shipment-info-pods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentInfoPOD() throws Exception {
        // Initialize the database
        shipmentInfoPODService.save(shipmentInfoPOD);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockShipmentInfoPODSearchRepository);

        int databaseSizeBeforeUpdate = shipmentInfoPODRepository.findAll().size();

        // Update the shipmentInfoPOD
        ShipmentInfoPOD updatedShipmentInfoPOD = shipmentInfoPODRepository.findById(shipmentInfoPOD.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentInfoPOD are not directly saved in db
        em.detach(updatedShipmentInfoPOD);
        updatedShipmentInfoPOD
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS);

        restShipmentInfoPODMockMvc.perform(put("/api/shipment-info-pods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipmentInfoPOD)))
            .andExpect(status().isOk());

        // Validate the ShipmentInfoPOD in the database
        List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository.findAll();
        assertThat(shipmentInfoPODList).hasSize(databaseSizeBeforeUpdate);
        ShipmentInfoPOD testShipmentInfoPOD = shipmentInfoPODList.get(shipmentInfoPODList.size() - 1);
        assertThat(testShipmentInfoPOD.getPod()).isEqualTo(UPDATED_POD);
        assertThat(testShipmentInfoPOD.getPodContentType()).isEqualTo(UPDATED_POD_CONTENT_TYPE);
        assertThat(testShipmentInfoPOD.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the ShipmentInfoPOD in Elasticsearch
        verify(mockShipmentInfoPODSearchRepository, times(1)).save(testShipmentInfoPOD);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentInfoPOD() throws Exception {
        int databaseSizeBeforeUpdate = shipmentInfoPODRepository.findAll().size();

        // Create the ShipmentInfoPOD

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentInfoPODMockMvc.perform(put("/api/shipment-info-pods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfoPOD)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentInfoPOD in the database
        List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository.findAll();
        assertThat(shipmentInfoPODList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentInfoPOD in Elasticsearch
        verify(mockShipmentInfoPODSearchRepository, times(0)).save(shipmentInfoPOD);
    }

    @Test
    @Transactional
    public void deleteShipmentInfoPOD() throws Exception {
        // Initialize the database
        shipmentInfoPODService.save(shipmentInfoPOD);

        int databaseSizeBeforeDelete = shipmentInfoPODRepository.findAll().size();

        // Delete the shipmentInfoPOD
        restShipmentInfoPODMockMvc.perform(delete("/api/shipment-info-pods/{id}", shipmentInfoPOD.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentInfoPOD> shipmentInfoPODList = shipmentInfoPODRepository.findAll();
        assertThat(shipmentInfoPODList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentInfoPOD in Elasticsearch
        verify(mockShipmentInfoPODSearchRepository, times(1)).deleteById(shipmentInfoPOD.getId());
    }

    @Test
    @Transactional
    public void searchShipmentInfoPOD() throws Exception {
        // Initialize the database
        shipmentInfoPODService.save(shipmentInfoPOD);
        when(mockShipmentInfoPODSearchRepository.search(queryStringQuery("id:" + shipmentInfoPOD.getId())))
            .thenReturn(Collections.singletonList(shipmentInfoPOD));
        // Search the shipmentInfoPOD
        restShipmentInfoPODMockMvc.perform(get("/api/_search/shipment-info-pods?query=id:" + shipmentInfoPOD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentInfoPOD.getId().intValue())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentInfoPOD.class);
        ShipmentInfoPOD shipmentInfoPOD1 = new ShipmentInfoPOD();
        shipmentInfoPOD1.setId(1L);
        ShipmentInfoPOD shipmentInfoPOD2 = new ShipmentInfoPOD();
        shipmentInfoPOD2.setId(shipmentInfoPOD1.getId());
        assertThat(shipmentInfoPOD1).isEqualTo(shipmentInfoPOD2);
        shipmentInfoPOD2.setId(2L);
        assertThat(shipmentInfoPOD1).isNotEqualTo(shipmentInfoPOD2);
        shipmentInfoPOD1.setId(null);
        assertThat(shipmentInfoPOD1).isNotEqualTo(shipmentInfoPOD2);
    }
}
