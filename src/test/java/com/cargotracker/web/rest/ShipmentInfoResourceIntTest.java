package com.cargotracker.web.rest;

import com.cargotracker.CargotrackerApp;

import com.cargotracker.domain.ShipmentInfo;
import com.cargotracker.repository.ShipmentInfoRepository;
import com.cargotracker.repository.search.ShipmentInfoSearchRepository;
import com.cargotracker.service.ShipmentInfoService;
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
import java.math.BigDecimal;
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
 * Test class for the ShipmentInfoResource REST controller.
 *
 * @see ShipmentInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargotrackerApp.class)
public class ShipmentInfoResourceIntTest {

    private static final String DEFAULT_CONSIGNMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONSIGNMENT_NO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BOOKING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BOOKING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPECTED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPECTED_DELIVERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_ACTUAL_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACTUAL_WEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VOLUMETRIC_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUMETRIC_WEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LENGTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_LENGTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_WIDTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_WIDTH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HEIGHT = new BigDecimal(2);

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_TOTAL_FRIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_FRIGHT = new BigDecimal(2);

    private static final String DEFAULT_PACKAGE_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_DESCIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_THIRD_PARTY = false;
    private static final Boolean UPDATED_IS_THIRD_PARTY = true;

    private static final String DEFAULT_CARRIER_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER_REF_NO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DELIVERED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RECEIVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION_SHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_SHIP = "BBBBBBBBBB";

    @Autowired
    private ShipmentInfoRepository shipmentInfoRepository;

    @Autowired
    private ShipmentInfoService shipmentInfoService;

    /**
     * This repository is mocked in the com.cargotracker.repository.search test package.
     *
     * @see com.cargotracker.repository.search.ShipmentInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ShipmentInfoSearchRepository mockShipmentInfoSearchRepository;

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

    private MockMvc restShipmentInfoMockMvc;

    private ShipmentInfo shipmentInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipmentInfoResource shipmentInfoResource = new ShipmentInfoResource(shipmentInfoService);
        this.restShipmentInfoMockMvc = MockMvcBuilders.standaloneSetup(shipmentInfoResource)
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
    public static ShipmentInfo createEntity(EntityManager em) {
        ShipmentInfo shipmentInfo = new ShipmentInfo()
            .consignmentNo(DEFAULT_CONSIGNMENT_NO)
            .bookingDate(DEFAULT_BOOKING_DATE)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .actualWeight(DEFAULT_ACTUAL_WEIGHT)
            .volumetricWeight(DEFAULT_VOLUMETRIC_WEIGHT)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .quantity(DEFAULT_QUANTITY)
            .totalFright(DEFAULT_TOTAL_FRIGHT)
            .packageDesciption(DEFAULT_PACKAGE_DESCIPTION)
            .isThirdParty(DEFAULT_IS_THIRD_PARTY)
            .carrierRefNo(DEFAULT_CARRIER_REF_NO)
            .deliveredDate(DEFAULT_DELIVERED_DATE)
            .receivedBy(DEFAULT_RECEIVED_BY)
            .relationShip(DEFAULT_RELATION_SHIP);
        return shipmentInfo;
    }

    @Before
    public void initTest() {
        shipmentInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipmentInfo() throws Exception {
        int databaseSizeBeforeCreate = shipmentInfoRepository.findAll().size();

        // Create the ShipmentInfo
        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isCreated());

        // Validate the ShipmentInfo in the database
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentInfo testShipmentInfo = shipmentInfoList.get(shipmentInfoList.size() - 1);
        assertThat(testShipmentInfo.getConsignmentNo()).isEqualTo(DEFAULT_CONSIGNMENT_NO);
        assertThat(testShipmentInfo.getBookingDate()).isEqualTo(DEFAULT_BOOKING_DATE);
        assertThat(testShipmentInfo.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testShipmentInfo.getActualWeight()).isEqualTo(DEFAULT_ACTUAL_WEIGHT);
        assertThat(testShipmentInfo.getVolumetricWeight()).isEqualTo(DEFAULT_VOLUMETRIC_WEIGHT);
        assertThat(testShipmentInfo.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testShipmentInfo.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testShipmentInfo.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testShipmentInfo.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShipmentInfo.getTotalFright()).isEqualTo(DEFAULT_TOTAL_FRIGHT);
        assertThat(testShipmentInfo.getPackageDesciption()).isEqualTo(DEFAULT_PACKAGE_DESCIPTION);
        assertThat(testShipmentInfo.isIsThirdParty()).isEqualTo(DEFAULT_IS_THIRD_PARTY);
        assertThat(testShipmentInfo.getCarrierRefNo()).isEqualTo(DEFAULT_CARRIER_REF_NO);
        assertThat(testShipmentInfo.getDeliveredDate()).isEqualTo(DEFAULT_DELIVERED_DATE);
        assertThat(testShipmentInfo.getReceivedBy()).isEqualTo(DEFAULT_RECEIVED_BY);
        assertThat(testShipmentInfo.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);

        // Validate the ShipmentInfo in Elasticsearch
        verify(mockShipmentInfoSearchRepository, times(1)).save(testShipmentInfo);
    }

    @Test
    @Transactional
    public void createShipmentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipmentInfoRepository.findAll().size();

        // Create the ShipmentInfo with an existing ID
        shipmentInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentInfo in the database
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ShipmentInfo in Elasticsearch
        verify(mockShipmentInfoSearchRepository, times(0)).save(shipmentInfo);
    }

    @Test
    @Transactional
    public void checkConsignmentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setConsignmentNo(null);

        // Create the ShipmentInfo, which fails.

        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setBookingDate(null);

        // Create the ShipmentInfo, which fails.

        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpectedDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setExpectedDeliveryDate(null);

        // Create the ShipmentInfo, which fails.

        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsThirdPartyIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setIsThirdParty(null);

        // Create the ShipmentInfo, which fails.

        restShipmentInfoMockMvc.perform(post("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShipmentInfos() throws Exception {
        // Initialize the database
        shipmentInfoRepository.saveAndFlush(shipmentInfo);

        // Get all the shipmentInfoList
        restShipmentInfoMockMvc.perform(get("/api/shipment-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].consignmentNo").value(hasItem(DEFAULT_CONSIGNMENT_NO.toString())))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(sameInstant(DEFAULT_BOOKING_DATE))))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].actualWeight").value(hasItem(DEFAULT_ACTUAL_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].volumetricWeight").value(hasItem(DEFAULT_VOLUMETRIC_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalFright").value(hasItem(DEFAULT_TOTAL_FRIGHT.intValue())))
            .andExpect(jsonPath("$.[*].packageDesciption").value(hasItem(DEFAULT_PACKAGE_DESCIPTION.toString())))
            .andExpect(jsonPath("$.[*].isThirdParty").value(hasItem(DEFAULT_IS_THIRD_PARTY.booleanValue())))
            .andExpect(jsonPath("$.[*].carrierRefNo").value(hasItem(DEFAULT_CARRIER_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(sameInstant(DEFAULT_DELIVERED_DATE))))
            .andExpect(jsonPath("$.[*].receivedBy").value(hasItem(DEFAULT_RECEIVED_BY.toString())))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP.toString())));
    }
    
    @Test
    @Transactional
    public void getShipmentInfo() throws Exception {
        // Initialize the database
        shipmentInfoRepository.saveAndFlush(shipmentInfo);

        // Get the shipmentInfo
        restShipmentInfoMockMvc.perform(get("/api/shipment-infos/{id}", shipmentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentInfo.getId().intValue()))
            .andExpect(jsonPath("$.consignmentNo").value(DEFAULT_CONSIGNMENT_NO.toString()))
            .andExpect(jsonPath("$.bookingDate").value(sameInstant(DEFAULT_BOOKING_DATE)))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE)))
            .andExpect(jsonPath("$.actualWeight").value(DEFAULT_ACTUAL_WEIGHT.intValue()))
            .andExpect(jsonPath("$.volumetricWeight").value(DEFAULT_VOLUMETRIC_WEIGHT.intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalFright").value(DEFAULT_TOTAL_FRIGHT.intValue()))
            .andExpect(jsonPath("$.packageDesciption").value(DEFAULT_PACKAGE_DESCIPTION.toString()))
            .andExpect(jsonPath("$.isThirdParty").value(DEFAULT_IS_THIRD_PARTY.booleanValue()))
            .andExpect(jsonPath("$.carrierRefNo").value(DEFAULT_CARRIER_REF_NO.toString()))
            .andExpect(jsonPath("$.deliveredDate").value(sameInstant(DEFAULT_DELIVERED_DATE)))
            .andExpect(jsonPath("$.receivedBy").value(DEFAULT_RECEIVED_BY.toString()))
            .andExpect(jsonPath("$.relationShip").value(DEFAULT_RELATION_SHIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShipmentInfo() throws Exception {
        // Get the shipmentInfo
        restShipmentInfoMockMvc.perform(get("/api/shipment-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipmentInfo() throws Exception {
        // Initialize the database
        shipmentInfoService.save(shipmentInfo);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockShipmentInfoSearchRepository);

        int databaseSizeBeforeUpdate = shipmentInfoRepository.findAll().size();

        // Update the shipmentInfo
        ShipmentInfo updatedShipmentInfo = shipmentInfoRepository.findById(shipmentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentInfo are not directly saved in db
        em.detach(updatedShipmentInfo);
        updatedShipmentInfo
            .consignmentNo(UPDATED_CONSIGNMENT_NO)
            .bookingDate(UPDATED_BOOKING_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .actualWeight(UPDATED_ACTUAL_WEIGHT)
            .volumetricWeight(UPDATED_VOLUMETRIC_WEIGHT)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .quantity(UPDATED_QUANTITY)
            .totalFright(UPDATED_TOTAL_FRIGHT)
            .packageDesciption(UPDATED_PACKAGE_DESCIPTION)
            .isThirdParty(UPDATED_IS_THIRD_PARTY)
            .carrierRefNo(UPDATED_CARRIER_REF_NO)
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .receivedBy(UPDATED_RECEIVED_BY)
            .relationShip(UPDATED_RELATION_SHIP);

        restShipmentInfoMockMvc.perform(put("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipmentInfo)))
            .andExpect(status().isOk());

        // Validate the ShipmentInfo in the database
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeUpdate);
        ShipmentInfo testShipmentInfo = shipmentInfoList.get(shipmentInfoList.size() - 1);
        assertThat(testShipmentInfo.getConsignmentNo()).isEqualTo(UPDATED_CONSIGNMENT_NO);
        assertThat(testShipmentInfo.getBookingDate()).isEqualTo(UPDATED_BOOKING_DATE);
        assertThat(testShipmentInfo.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testShipmentInfo.getActualWeight()).isEqualTo(UPDATED_ACTUAL_WEIGHT);
        assertThat(testShipmentInfo.getVolumetricWeight()).isEqualTo(UPDATED_VOLUMETRIC_WEIGHT);
        assertThat(testShipmentInfo.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testShipmentInfo.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testShipmentInfo.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testShipmentInfo.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShipmentInfo.getTotalFright()).isEqualTo(UPDATED_TOTAL_FRIGHT);
        assertThat(testShipmentInfo.getPackageDesciption()).isEqualTo(UPDATED_PACKAGE_DESCIPTION);
        assertThat(testShipmentInfo.isIsThirdParty()).isEqualTo(UPDATED_IS_THIRD_PARTY);
        assertThat(testShipmentInfo.getCarrierRefNo()).isEqualTo(UPDATED_CARRIER_REF_NO);
        assertThat(testShipmentInfo.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testShipmentInfo.getReceivedBy()).isEqualTo(UPDATED_RECEIVED_BY);
        assertThat(testShipmentInfo.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);

        // Validate the ShipmentInfo in Elasticsearch
        verify(mockShipmentInfoSearchRepository, times(1)).save(testShipmentInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingShipmentInfo() throws Exception {
        int databaseSizeBeforeUpdate = shipmentInfoRepository.findAll().size();

        // Create the ShipmentInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentInfoMockMvc.perform(put("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipmentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentInfo in the database
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ShipmentInfo in Elasticsearch
        verify(mockShipmentInfoSearchRepository, times(0)).save(shipmentInfo);
    }

    @Test
    @Transactional
    public void deleteShipmentInfo() throws Exception {
        // Initialize the database
        shipmentInfoService.save(shipmentInfo);

        int databaseSizeBeforeDelete = shipmentInfoRepository.findAll().size();

        // Delete the shipmentInfo
        restShipmentInfoMockMvc.perform(delete("/api/shipment-infos/{id}", shipmentInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ShipmentInfo in Elasticsearch
        verify(mockShipmentInfoSearchRepository, times(1)).deleteById(shipmentInfo.getId());
    }

    @Test
    @Transactional
    public void searchShipmentInfo() throws Exception {
        // Initialize the database
        shipmentInfoService.save(shipmentInfo);
        when(mockShipmentInfoSearchRepository.search(queryStringQuery("id:" + shipmentInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(shipmentInfo), PageRequest.of(0, 1), 1));
        // Search the shipmentInfo
        restShipmentInfoMockMvc.perform(get("/api/_search/shipment-infos?query=id:" + shipmentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].consignmentNo").value(hasItem(DEFAULT_CONSIGNMENT_NO)))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(sameInstant(DEFAULT_BOOKING_DATE))))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].actualWeight").value(hasItem(DEFAULT_ACTUAL_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].volumetricWeight").value(hasItem(DEFAULT_VOLUMETRIC_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalFright").value(hasItem(DEFAULT_TOTAL_FRIGHT.intValue())))
            .andExpect(jsonPath("$.[*].packageDesciption").value(hasItem(DEFAULT_PACKAGE_DESCIPTION)))
            .andExpect(jsonPath("$.[*].isThirdParty").value(hasItem(DEFAULT_IS_THIRD_PARTY.booleanValue())))
            .andExpect(jsonPath("$.[*].carrierRefNo").value(hasItem(DEFAULT_CARRIER_REF_NO)))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(sameInstant(DEFAULT_DELIVERED_DATE))))
            .andExpect(jsonPath("$.[*].receivedBy").value(hasItem(DEFAULT_RECEIVED_BY)))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentInfo.class);
        ShipmentInfo shipmentInfo1 = new ShipmentInfo();
        shipmentInfo1.setId(1L);
        ShipmentInfo shipmentInfo2 = new ShipmentInfo();
        shipmentInfo2.setId(shipmentInfo1.getId());
        assertThat(shipmentInfo1).isEqualTo(shipmentInfo2);
        shipmentInfo2.setId(2L);
        assertThat(shipmentInfo1).isNotEqualTo(shipmentInfo2);
        shipmentInfo1.setId(null);
        assertThat(shipmentInfo1).isNotEqualTo(shipmentInfo2);
    }
}
