package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShipmentMode;
import com.cargotracker.repository.ShipmentModeRepository;
//import com.cargotracker.repository.search.ShipmentModeSearchRepository;
import com.cargotracker.service.ShipmentModeService;
import com.cargotracker.service.dto.ShipmentModeDTO;
import com.cargotracker.service.mapper.ShipmentModeMapper;
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
//import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShipmentModeResource REST controller.
 *
 * @see ShipmentModeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShipmentModeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private ShipmentModeRepository shipmentModeRepository;

    @Autowired
    private ShipmentModeMapper shipmentModeMapper;

    @Autowired
    private ShipmentModeService shipmentModeService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShipmentModeSearchRepositoryMockConfiguration
     */
    //@Autowired
    //private ShipmentModeSearchRepository mockShipmentModeSearchRepository;

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

    private MockMvc restShipmentModeMockMvc;

    private ShipmentMode shipmentMode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentModeResource shipmentModeResource = new ShipmentModeResource(shipmentModeService);
        this.restShipmentModeMockMvc = MockMvcBuilders.standaloneSetup(shipmentModeResource)
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
    public static ShipmentMode createEntity(EntityManager em) {
        ShipmentMode shipmentMode = new ShipmentMode()
            .value(DEFAULT_VALUE)
            .desc(DEFAULT_DESC);
        return shipmentMode;
    }

    @Before
    public void initTest() {
        shipmentMode = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentMode() throws Exception {
        int databaseSizeBeforeCreate = shipmentModeRepository.findAll().size();

        // Create the ShipmentMode
        ShipmentModeDTO shipmentModeDTO = shipmentModeMapper.toDto(shipmentMode);
        restShipmentModeMockMvc.perform(post("/api/shipment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentModeDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentMode in the database
        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentMode testShipmentMode = shipmentModeList.get(shipmentModeList.size() - 1);
        assertThat(testShipmentMode.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testShipmentMode.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the ShipmentMode in Elasticsearch
        //verify(mockShipmentModeSearchRepository, times(1)).save(testShipmentMode);
    }

    @Test
    @Transactional
    public void createShipmentModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentModeRepository.findAll().size();

        // Create the ShipmentMode with an existing ID
        shipmentMode.setId(1L);
        ShipmentModeDTO shipmentModeDTO = shipmentModeMapper.toDto(shipmentMode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentModeMockMvc.perform(post("/api/shipment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentModeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentMode in the database
        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentMode in Elasticsearch
        //verify(mockShipmentModeSearchRepository, times(0)).save(shipmentMode);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentModeRepository.findAll().size();
        // set the field null
        shipmentMode.setValue(null);

        // Create the ShipmentMode, which fails.
        ShipmentModeDTO shipmentModeDTO = shipmentModeMapper.toDto(shipmentMode);

        restShipmentModeMockMvc.perform(post("/api/shipment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentModeDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShipmentModes() throws Exception {
        // Initialize the database
        shipmentModeRepository.saveAndFlush(shipmentMode);

        // Get all the shipmentModeList
        restShipmentModeMockMvc.perform(get("/api/shipment-modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getShipmentMode() throws Exception {
        // Initialize the database
        shipmentModeRepository.saveAndFlush(shipmentMode);

        // Get the shipmentMode
        restShipmentModeMockMvc.perform(get("/api/shipment-modes/{id}", shipmentMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentMode.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentMode() throws Exception {
        // Get the shipmentMode
        restShipmentModeMockMvc.perform(get("/api/shipment-modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentMode() throws Exception {
        // Initialize the database
        shipmentModeRepository.saveAndFlush(shipmentMode);

        int databaseSizeBeforeUpdate = shipmentModeRepository.findAll().size();

        // Update the shipmentMode
        ShipmentMode updatedShipmentMode = shipmentModeRepository.findById(shipmentMode.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentMode are not directly saved in db
        em.detach(updatedShipmentMode);
        updatedShipmentMode
            .value(UPDATED_VALUE)
            .desc(UPDATED_DESC);
        ShipmentModeDTO shipmentModeDTO = shipmentModeMapper.toDto(updatedShipmentMode);

        restShipmentModeMockMvc.perform(put("/api/shipment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentModeDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentMode in the database
        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeUpdate);
        ShipmentMode testShipmentMode = shipmentModeList.get(shipmentModeList.size() - 1);
        assertThat(testShipmentMode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testShipmentMode.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the ShipmentMode in Elasticsearch
        //verify(mockShipmentModeSearchRepository, times(1)).save(testShipmentMode);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentMode() throws Exception {
        int databaseSizeBeforeUpdate = shipmentModeRepository.findAll().size();

        // Create the ShipmentMode
        ShipmentModeDTO shipmentModeDTO = shipmentModeMapper.toDto(shipmentMode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentModeMockMvc.perform(put("/api/shipment-modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentModeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentMode in the database
        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentMode in Elasticsearch
        //verify(mockShipmentModeSearchRepository, times(0)).save(shipmentMode);
    }

    @Test
    @Transactional
    public void deleteShipmentMode() throws Exception {
        // Initialize the database
        shipmentModeRepository.saveAndFlush(shipmentMode);

        int databaseSizeBeforeDelete = shipmentModeRepository.findAll().size();

        // Delete the shipmentMode
        restShipmentModeMockMvc.perform(delete("/api/shipment-modes/{id}", shipmentMode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentMode> shipmentModeList = shipmentModeRepository.findAll();
        assertThat(shipmentModeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentMode in Elasticsearch
        //verify(mockShipmentModeSearchRepository, times(1)).deleteById(shipmentMode.getId());
    }

    /*@Test
    @Transactional
    public void searchShipmentMode() throws Exception {
        // Initialize the database
        shipmentModeRepository.saveAndFlush(shipmentMode);
        when(mockShipmentModeSearchRepository.search(queryStringQuery("id:" + shipmentMode.getId())))
            .thenReturn(Collections.singletonList(shipmentMode));
        // Search the shipmentMode
        restShipmentModeMockMvc.perform(get("/api/_search/shipment-modes?query=id:" + shipmentMode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentMode.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }*/

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentMode.class);
        ShipmentMode shipmentMode1 = new ShipmentMode();
        shipmentMode1.setId(1L);
        ShipmentMode shipmentMode2 = new ShipmentMode();
        shipmentMode2.setId(shipmentMode1.getId());
        assertThat(shipmentMode1).isEqualTo(shipmentMode2);
        shipmentMode2.setId(2L);
        assertThat(shipmentMode1).isNotEqualTo(shipmentMode2);
        shipmentMode1.setId(null);
        assertThat(shipmentMode1).isNotEqualTo(shipmentMode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentModeDTO.class);
        ShipmentModeDTO shipmentModeDTO1 = new ShipmentModeDTO();
        shipmentModeDTO1.setId(1L);
        ShipmentModeDTO shipmentModeDTO2 = new ShipmentModeDTO();
        assertThat(shipmentModeDTO1).isNotEqualTo(shipmentModeDTO2);
        shipmentModeDTO2.setId(shipmentModeDTO1.getId());
        assertThat(shipmentModeDTO1).isEqualTo(shipmentModeDTO2);
        shipmentModeDTO2.setId(2L);
        assertThat(shipmentModeDTO1).isNotEqualTo(shipmentModeDTO2);
        shipmentModeDTO1.setId(null);
        assertThat(shipmentModeDTO1).isNotEqualTo(shipmentModeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentModeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentModeMapper.fromId(null)).isNull();
    }
}
