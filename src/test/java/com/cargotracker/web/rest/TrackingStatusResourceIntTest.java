package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.TrackingStatus;
import com.cargotracker.repository.TrackingStatusRepository;
//import com.cargotracker.repository.search.TrackingStatusSearchRepository;
import com.cargotracker.service.TrackingStatusService;
import com.cargotracker.service.dto.TrackingStatusDTO;
import com.cargotracker.service.mapper.TrackingStatusMapper;
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
 * Test class for the TrackingStatusResource REST controller.
 *
 * @see TrackingStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class TrackingStatusResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private TrackingStatusRepository trackingStatusRepository;

    @Autowired
    private TrackingStatusMapper trackingStatusMapper;

    @Autowired
    private TrackingStatusService trackingStatusService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.TrackingStatusSearchRepositoryMockConfiguration
     */
    //@Autowired
    //private TrackingStatusSearchRepository mockTrackingStatusSearchRepository;

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

    private MockMvc restTrackingStatusMockMvc;

    private TrackingStatus trackingStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrackingStatusResource trackingStatusResource = new TrackingStatusResource(trackingStatusService);
        this.restTrackingStatusMockMvc = MockMvcBuilders.standaloneSetup(trackingStatusResource)
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
    public static TrackingStatus createEntity(EntityManager em) {
        TrackingStatus trackingStatus = new TrackingStatus()
            .value(DEFAULT_VALUE)
            .desc(DEFAULT_DESC);
        return trackingStatus;
    }

    @Before
    public void initTest() {
        trackingStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrackingStatus() throws Exception {
        int databaseSizeBeforeCreate = trackingStatusRepository.findAll().size();

        // Create the TrackingStatus
        TrackingStatusDTO trackingStatusDTO = trackingStatusMapper.toDto(trackingStatus);
        restTrackingStatusMockMvc.perform(post("/api/tracking-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TrackingStatus in the database
        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TrackingStatus testTrackingStatus = trackingStatusList.get(trackingStatusList.size() - 1);
        assertThat(testTrackingStatus.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTrackingStatus.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the TrackingStatus in Elasticsearch
        //verify(mockTrackingStatusSearchRepository, times(1)).save(testTrackingStatus);
    }

    @Test
    @Transactional
    public void createTrackingStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackingStatusRepository.findAll().size();

        // Create the TrackingStatus with an existing ID
        trackingStatus.setId(1L);
        TrackingStatusDTO trackingStatusDTO = trackingStatusMapper.toDto(trackingStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackingStatusMockMvc.perform(post("/api/tracking-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingStatus in the database
        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrackingStatus in Elasticsearch
        //verify(mockTrackingStatusSearchRepository, times(0)).save(trackingStatus);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackingStatusRepository.findAll().size();
        // set the field null
        trackingStatus.setValue(null);

        // Create the TrackingStatus, which fails.
        TrackingStatusDTO trackingStatusDTO = trackingStatusMapper.toDto(trackingStatus);

        restTrackingStatusMockMvc.perform(post("/api/tracking-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingStatusDTO)))
            .andExpect(status().isBadRequest());

        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrackingStatuses() throws Exception {
        // Initialize the database
        trackingStatusRepository.saveAndFlush(trackingStatus);

        // Get all the trackingStatusList
        restTrackingStatusMockMvc.perform(get("/api/tracking-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getTrackingStatus() throws Exception {
        // Initialize the database
        trackingStatusRepository.saveAndFlush(trackingStatus);

        // Get the trackingStatus
        restTrackingStatusMockMvc.perform(get("/api/tracking-statuses/{id}", trackingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trackingStatus.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrackingStatus() throws Exception {
        // Get the trackingStatus
        restTrackingStatusMockMvc.perform(get("/api/tracking-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrackingStatus() throws Exception {
        // Initialize the database
        trackingStatusRepository.saveAndFlush(trackingStatus);

        int databaseSizeBeforeUpdate = trackingStatusRepository.findAll().size();

        // Update the trackingStatus
        TrackingStatus updatedTrackingStatus = trackingStatusRepository.findById(trackingStatus.getId()).get();
        // Disconnect from session so that the updates on updatedTrackingStatus are not directly saved in db
        em.detach(updatedTrackingStatus);
        updatedTrackingStatus
            .value(UPDATED_VALUE)
            .desc(UPDATED_DESC);
        TrackingStatusDTO trackingStatusDTO = trackingStatusMapper.toDto(updatedTrackingStatus);

        restTrackingStatusMockMvc.perform(put("/api/tracking-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingStatusDTO)))
            .andExpect(status().isOk());

        // Validate the TrackingStatus in the database
        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeUpdate);
        TrackingStatus testTrackingStatus = trackingStatusList.get(trackingStatusList.size() - 1);
        assertThat(testTrackingStatus.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTrackingStatus.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the TrackingStatus in Elasticsearch
        //verify(mockTrackingStatusSearchRepository, times(1)).save(testTrackingStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingTrackingStatus() throws Exception {
        int databaseSizeBeforeUpdate = trackingStatusRepository.findAll().size();

        // Create the TrackingStatus
        TrackingStatusDTO trackingStatusDTO = trackingStatusMapper.toDto(trackingStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackingStatusMockMvc.perform(put("/api/tracking-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrackingStatus in the database
        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrackingStatus in Elasticsearch
        //verify(mockTrackingStatusSearchRepository, times(0)).save(trackingStatus);
    }

    @Test
    @Transactional
    public void deleteTrackingStatus() throws Exception {
        // Initialize the database
        trackingStatusRepository.saveAndFlush(trackingStatus);

        int databaseSizeBeforeDelete = trackingStatusRepository.findAll().size();

        // Delete the trackingStatus
        restTrackingStatusMockMvc.perform(delete("/api/tracking-statuses/{id}", trackingStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrackingStatus> trackingStatusList = trackingStatusRepository.findAll();
        assertThat(trackingStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrackingStatus in Elasticsearch
        //verify(mockTrackingStatusSearchRepository, times(1)).deleteById(trackingStatus.getId());
    }

    /*@Test
    @Transactional
    public void searchTrackingStatus() throws Exception {
        // Initialize the database
        trackingStatusRepository.saveAndFlush(trackingStatus);
        when(mockTrackingStatusSearchRepository.search(queryStringQuery("id:" + trackingStatus.getId())))
            .thenReturn(Collections.singletonList(trackingStatus));
        // Search the trackingStatus
        restTrackingStatusMockMvc.perform(get("/api/_search/tracking-statuses?query=id:" + trackingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trackingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }*/

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingStatus.class);
        TrackingStatus trackingStatus1 = new TrackingStatus();
        trackingStatus1.setId(1L);
        TrackingStatus trackingStatus2 = new TrackingStatus();
        trackingStatus2.setId(trackingStatus1.getId());
        assertThat(trackingStatus1).isEqualTo(trackingStatus2);
        trackingStatus2.setId(2L);
        assertThat(trackingStatus1).isNotEqualTo(trackingStatus2);
        trackingStatus1.setId(null);
        assertThat(trackingStatus1).isNotEqualTo(trackingStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingStatusDTO.class);
        TrackingStatusDTO trackingStatusDTO1 = new TrackingStatusDTO();
        trackingStatusDTO1.setId(1L);
        TrackingStatusDTO trackingStatusDTO2 = new TrackingStatusDTO();
        assertThat(trackingStatusDTO1).isNotEqualTo(trackingStatusDTO2);
        trackingStatusDTO2.setId(trackingStatusDTO1.getId());
        assertThat(trackingStatusDTO1).isEqualTo(trackingStatusDTO2);
        trackingStatusDTO2.setId(2L);
        assertThat(trackingStatusDTO1).isNotEqualTo(trackingStatusDTO2);
        trackingStatusDTO1.setId(null);
        assertThat(trackingStatusDTO1).isNotEqualTo(trackingStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trackingStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trackingStatusMapper.fromId(null)).isNull();
    }
}
