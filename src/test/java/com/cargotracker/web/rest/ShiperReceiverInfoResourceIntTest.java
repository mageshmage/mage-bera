package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShiperReceiverInfo;
import com.cargotracker.repository.ShiperReceiverInfoRepository;
import com.cargotracker.repository.search.ShiperReceiverInfoSearchRepository;
import com.cargotracker.service.ShiperReceiverInfoService;
import com.cargotracker.service.dto.ShiperReceiverInfoDTO;
import com.cargotracker.service.mapper.ShiperReceiverInfoMapper;
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
import java.util.Collections;
import java.util.List;


import static com.cargotracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cargotracker.domain.enumeration.ShiperReceiverType;
/**
 * Test class for the ShiperReceiverInfoResource REST controller.
 *
 * @see ShiperReceiverInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShiperReceiverInfoResourceIntTest {

    private static final ShiperReceiverType DEFAULT_TYPE = ShiperReceiverType.CONSIGNOR;
    private static final ShiperReceiverType UPDATED_TYPE = ShiperReceiverType.CONSIGNEE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    @Autowired
    private ShiperReceiverInfoRepository shiperReceiverInfoRepository;

    @Autowired
    private ShiperReceiverInfoMapper shiperReceiverInfoMapper;

    @Autowired
    private ShiperReceiverInfoService shiperReceiverInfoService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShiperReceiverInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShiperReceiverInfoSearchRepository mockShiperReceiverInfoSearchRepository;

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

    private MockMvc restShiperReceiverInfoMockMvc;

    private ShiperReceiverInfo shiperReceiverInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShiperReceiverInfoResource shiperReceiverInfoResource = new ShiperReceiverInfoResource(shiperReceiverInfoService);
        this.restShiperReceiverInfoMockMvc = MockMvcBuilders.standaloneSetup(shiperReceiverInfoResource)
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
    public static ShiperReceiverInfo createEntity(EntityManager em) {
        ShiperReceiverInfo shiperReceiverInfo = new ShiperReceiverInfo()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .phoneNo(DEFAULT_PHONE_NO)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .pincode(DEFAULT_PINCODE)
            .emailId(DEFAULT_EMAIL_ID);
        return shiperReceiverInfo;
    }

    @Before
    public void initTest() {
        shiperReceiverInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createShiperReceiverInfo() throws Exception {
        int databaseSizeBeforeCreate = shiperReceiverInfoRepository.findAll().size();

        // Create the ShiperReceiverInfo
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);
        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ShiperReceiverInfo in the database
        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ShiperReceiverInfo testShiperReceiverInfo = shiperReceiverInfoList.get(shiperReceiverInfoList.size() - 1);
        assertThat(testShiperReceiverInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testShiperReceiverInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShiperReceiverInfo.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testShiperReceiverInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShiperReceiverInfo.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShiperReceiverInfo.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testShiperReceiverInfo.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);

        // Validate the ShiperReceiverInfo in Elasticsearch
        verify(mockShiperReceiverInfoSearchRepository, times(1)).save(testShiperReceiverInfo);
    }

    @Test
    @Transactional
    public void createShiperReceiverInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shiperReceiverInfoRepository.findAll().size();

        // Create the ShiperReceiverInfo with an existing ID
        shiperReceiverInfo.setId(1L);
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShiperReceiverInfo in the database
        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShiperReceiverInfo in Elasticsearch
        verify(mockShiperReceiverInfoSearchRepository, times(0)).save(shiperReceiverInfo);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shiperReceiverInfoRepository.findAll().size();
        // set the field null
        shiperReceiverInfo.setType(null);

        // Create the ShiperReceiverInfo, which fails.
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shiperReceiverInfoRepository.findAll().size();
        // set the field null
        shiperReceiverInfo.setName(null);

        // Create the ShiperReceiverInfo, which fails.
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = shiperReceiverInfoRepository.findAll().size();
        // set the field null
        shiperReceiverInfo.setPhoneNo(null);

        // Create the ShiperReceiverInfo, which fails.
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = shiperReceiverInfoRepository.findAll().size();
        // set the field null
        shiperReceiverInfo.setAddress(null);

        // Create the ShiperReceiverInfo, which fails.
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shiperReceiverInfoRepository.findAll().size();
        // set the field null
        shiperReceiverInfo.setPincode(null);

        // Create the ShiperReceiverInfo, which fails.
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(post("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShiperReceiverInfos() throws Exception {
        // Initialize the database
        shiperReceiverInfoRepository.saveAndFlush(shiperReceiverInfo);

        // Get all the shiperReceiverInfoList
        restShiperReceiverInfoMockMvc.perform(get("/api/shiper-receiver-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shiperReceiverInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getShiperReceiverInfo() throws Exception {
        // Initialize the database
        shiperReceiverInfoRepository.saveAndFlush(shiperReceiverInfo);

        // Get the shiperReceiverInfo
        restShiperReceiverInfoMockMvc.perform(get("/api/shiper-receiver-infos/{id}", shiperReceiverInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shiperReceiverInfo.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShiperReceiverInfo() throws Exception {
        // Get the shiperReceiverInfo
        restShiperReceiverInfoMockMvc.perform(get("/api/shiper-receiver-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShiperReceiverInfo() throws Exception {
        // Initialize the database
        shiperReceiverInfoRepository.saveAndFlush(shiperReceiverInfo);

        int databaseSizeBeforeUpdate = shiperReceiverInfoRepository.findAll().size();

        // Update the shiperReceiverInfo
        ShiperReceiverInfo updatedShiperReceiverInfo = shiperReceiverInfoRepository.findById(shiperReceiverInfo.getId()).get();
        // Disconnect from session so that the updates on updatedShiperReceiverInfo are not directly saved in db
        em.detach(updatedShiperReceiverInfo);
        updatedShiperReceiverInfo
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .phoneNo(UPDATED_PHONE_NO)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .pincode(UPDATED_PINCODE)
            .emailId(UPDATED_EMAIL_ID);
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(updatedShiperReceiverInfo);

        restShiperReceiverInfoMockMvc.perform(put("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isOk());

        // Validate the ShiperReceiverInfo in the database
        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeUpdate);
        ShiperReceiverInfo testShiperReceiverInfo = shiperReceiverInfoList.get(shiperReceiverInfoList.size() - 1);
        assertThat(testShiperReceiverInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testShiperReceiverInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShiperReceiverInfo.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testShiperReceiverInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShiperReceiverInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShiperReceiverInfo.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testShiperReceiverInfo.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);

        // Validate the ShiperReceiverInfo in Elasticsearch
        verify(mockShiperReceiverInfoSearchRepository, times(1)).save(testShiperReceiverInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingShiperReceiverInfo() throws Exception {
        int databaseSizeBeforeUpdate = shiperReceiverInfoRepository.findAll().size();

        // Create the ShiperReceiverInfo
        ShiperReceiverInfoDTO shiperReceiverInfoDTO = shiperReceiverInfoMapper.toDto(shiperReceiverInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShiperReceiverInfoMockMvc.perform(put("/api/shiper-receiver-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shiperReceiverInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShiperReceiverInfo in the database
        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShiperReceiverInfo in Elasticsearch
        verify(mockShiperReceiverInfoSearchRepository, times(0)).save(shiperReceiverInfo);
    }

    @Test
    @Transactional
    public void deleteShiperReceiverInfo() throws Exception {
        // Initialize the database
        shiperReceiverInfoRepository.saveAndFlush(shiperReceiverInfo);

        int databaseSizeBeforeDelete = shiperReceiverInfoRepository.findAll().size();

        // Delete the shiperReceiverInfo
        restShiperReceiverInfoMockMvc.perform(delete("/api/shiper-receiver-infos/{id}", shiperReceiverInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShiperReceiverInfo> shiperReceiverInfoList = shiperReceiverInfoRepository.findAll();
        assertThat(shiperReceiverInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShiperReceiverInfo in Elasticsearch
        verify(mockShiperReceiverInfoSearchRepository, times(1)).deleteById(shiperReceiverInfo.getId());
    }

    @Test
    @Transactional
    public void searchShiperReceiverInfo() throws Exception {
        // Initialize the database
        shiperReceiverInfoRepository.saveAndFlush(shiperReceiverInfo);
        when(mockShiperReceiverInfoSearchRepository.search(queryStringQuery("id:" + shiperReceiverInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(shiperReceiverInfo), PageRequest.of(0, 1), 1));
        // Search the shiperReceiverInfo
        restShiperReceiverInfoMockMvc.perform(get("/api/_search/shiper-receiver-infos?query=id:" + shiperReceiverInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shiperReceiverInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShiperReceiverInfo.class);
        ShiperReceiverInfo shiperReceiverInfo1 = new ShiperReceiverInfo();
        shiperReceiverInfo1.setId(1L);
        ShiperReceiverInfo shiperReceiverInfo2 = new ShiperReceiverInfo();
        shiperReceiverInfo2.setId(shiperReceiverInfo1.getId());
        assertThat(shiperReceiverInfo1).isEqualTo(shiperReceiverInfo2);
        shiperReceiverInfo2.setId(2L);
        assertThat(shiperReceiverInfo1).isNotEqualTo(shiperReceiverInfo2);
        shiperReceiverInfo1.setId(null);
        assertThat(shiperReceiverInfo1).isNotEqualTo(shiperReceiverInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShiperReceiverInfoDTO.class);
        ShiperReceiverInfoDTO shiperReceiverInfoDTO1 = new ShiperReceiverInfoDTO();
        shiperReceiverInfoDTO1.setId(1L);
        ShiperReceiverInfoDTO shiperReceiverInfoDTO2 = new ShiperReceiverInfoDTO();
        assertThat(shiperReceiverInfoDTO1).isNotEqualTo(shiperReceiverInfoDTO2);
        shiperReceiverInfoDTO2.setId(shiperReceiverInfoDTO1.getId());
        assertThat(shiperReceiverInfoDTO1).isEqualTo(shiperReceiverInfoDTO2);
        shiperReceiverInfoDTO2.setId(2L);
        assertThat(shiperReceiverInfoDTO1).isNotEqualTo(shiperReceiverInfoDTO2);
        shiperReceiverInfoDTO1.setId(null);
        assertThat(shiperReceiverInfoDTO1).isNotEqualTo(shiperReceiverInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shiperReceiverInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shiperReceiverInfoMapper.fromId(null)).isNull();
    }
}
