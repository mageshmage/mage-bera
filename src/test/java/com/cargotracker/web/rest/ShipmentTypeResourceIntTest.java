package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShipmentType;
import com.cargotracker.repository.ShipmentTypeRepository;
import com.cargotracker.repository.search.ShipmentTypeSearchRepository;
import com.cargotracker.service.ShipmentTypeService;
import com.cargotracker.service.dto.ShipmentTypeDTO;
import com.cargotracker.service.mapper.ShipmentTypeMapper;
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
 * Test class for the ShipmentTypeResource REST controller.
 *
 * @see ShipmentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShipmentTypeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private ShipmentTypeRepository shipmentTypeRepository;

    @Autowired
    private ShipmentTypeMapper shipmentTypeMapper;

    @Autowired
    private ShipmentTypeService shipmentTypeService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShipmentTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipmentTypeSearchRepository mockShipmentTypeSearchRepository;

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

    private MockMvc restShipmentTypeMockMvc;

    private ShipmentType shipmentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentTypeResource shipmentTypeResource = new ShipmentTypeResource(shipmentTypeService);
        this.restShipmentTypeMockMvc = MockMvcBuilders.standaloneSetup(shipmentTypeResource)
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
    public static ShipmentType createEntity(EntityManager em) {
        ShipmentType shipmentType = new ShipmentType()
            .value(DEFAULT_VALUE)
            .desc(DEFAULT_DESC);
        return shipmentType;
    }

    @Before
    public void initTest() {
        shipmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentType() throws Exception {
        int databaseSizeBeforeCreate = shipmentTypeRepository.findAll().size();

        // Create the ShipmentType
        ShipmentTypeDTO shipmentTypeDTO = shipmentTypeMapper.toDto(shipmentType);
        restShipmentTypeMockMvc.perform(post("/api/shipment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentType testShipmentType = shipmentTypeList.get(shipmentTypeList.size() - 1);
        assertThat(testShipmentType.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testShipmentType.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the ShipmentType in Elasticsearch
        verify(mockShipmentTypeSearchRepository, times(1)).save(testShipmentType);
    }

    @Test
    @Transactional
    public void createShipmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentTypeRepository.findAll().size();

        // Create the ShipmentType with an existing ID
        shipmentType.setId(1L);
        ShipmentTypeDTO shipmentTypeDTO = shipmentTypeMapper.toDto(shipmentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentTypeMockMvc.perform(post("/api/shipment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentType in Elasticsearch
        verify(mockShipmentTypeSearchRepository, times(0)).save(shipmentType);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentTypeRepository.findAll().size();
        // set the field null
        shipmentType.setValue(null);

        // Create the ShipmentType, which fails.
        ShipmentTypeDTO shipmentTypeDTO = shipmentTypeMapper.toDto(shipmentType);

        restShipmentTypeMockMvc.perform(post("/api/shipment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShipmentTypes() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get all the shipmentTypeList
        restShipmentTypeMockMvc.perform(get("/api/shipment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipment-types/{id}", shipmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentType.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentType() throws Exception {
        // Get the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/shipment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        int databaseSizeBeforeUpdate = shipmentTypeRepository.findAll().size();

        // Update the shipmentType
        ShipmentType updatedShipmentType = shipmentTypeRepository.findById(shipmentType.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentType are not directly saved in db
        em.detach(updatedShipmentType);
        updatedShipmentType
            .value(UPDATED_VALUE)
            .desc(UPDATED_DESC);
        ShipmentTypeDTO shipmentTypeDTO = shipmentTypeMapper.toDto(updatedShipmentType);

        restShipmentTypeMockMvc.perform(put("/api/shipment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeUpdate);
        ShipmentType testShipmentType = shipmentTypeList.get(shipmentTypeList.size() - 1);
        assertThat(testShipmentType.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testShipmentType.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the ShipmentType in Elasticsearch
        verify(mockShipmentTypeSearchRepository, times(1)).save(testShipmentType);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentType() throws Exception {
        int databaseSizeBeforeUpdate = shipmentTypeRepository.findAll().size();

        // Create the ShipmentType
        ShipmentTypeDTO shipmentTypeDTO = shipmentTypeMapper.toDto(shipmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentTypeMockMvc.perform(put("/api/shipment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentType in the database
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentType in Elasticsearch
        verify(mockShipmentTypeSearchRepository, times(0)).save(shipmentType);
    }

    @Test
    @Transactional
    public void deleteShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);

        int databaseSizeBeforeDelete = shipmentTypeRepository.findAll().size();

        // Delete the shipmentType
        restShipmentTypeMockMvc.perform(delete("/api/shipment-types/{id}", shipmentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentType> shipmentTypeList = shipmentTypeRepository.findAll();
        assertThat(shipmentTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentType in Elasticsearch
        verify(mockShipmentTypeSearchRepository, times(1)).deleteById(shipmentType.getId());
    }

    @Test
    @Transactional
    public void searchShipmentType() throws Exception {
        // Initialize the database
        shipmentTypeRepository.saveAndFlush(shipmentType);
        when(mockShipmentTypeSearchRepository.search(queryStringQuery("id:" + shipmentType.getId())))
            .thenReturn(Collections.singletonList(shipmentType));
        // Search the shipmentType
        restShipmentTypeMockMvc.perform(get("/api/_search/shipment-types?query=id:" + shipmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentType.class);
        ShipmentType shipmentType1 = new ShipmentType();
        shipmentType1.setId(1L);
        ShipmentType shipmentType2 = new ShipmentType();
        shipmentType2.setId(shipmentType1.getId());
        assertThat(shipmentType1).isEqualTo(shipmentType2);
        shipmentType2.setId(2L);
        assertThat(shipmentType1).isNotEqualTo(shipmentType2);
        shipmentType1.setId(null);
        assertThat(shipmentType1).isNotEqualTo(shipmentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentTypeDTO.class);
        ShipmentTypeDTO shipmentTypeDTO1 = new ShipmentTypeDTO();
        shipmentTypeDTO1.setId(1L);
        ShipmentTypeDTO shipmentTypeDTO2 = new ShipmentTypeDTO();
        assertThat(shipmentTypeDTO1).isNotEqualTo(shipmentTypeDTO2);
        shipmentTypeDTO2.setId(shipmentTypeDTO1.getId());
        assertThat(shipmentTypeDTO1).isEqualTo(shipmentTypeDTO2);
        shipmentTypeDTO2.setId(2L);
        assertThat(shipmentTypeDTO1).isNotEqualTo(shipmentTypeDTO2);
        shipmentTypeDTO1.setId(null);
        assertThat(shipmentTypeDTO1).isNotEqualTo(shipmentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipmentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipmentTypeMapper.fromId(null)).isNull();
    }
}
