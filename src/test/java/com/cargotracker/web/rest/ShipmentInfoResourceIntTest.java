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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.cargotracker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cargotracker.domain.enumeration.CARRIER;
import com.cargotracker.domain.enumeration.ShipmentType;
import com.cargotracker.domain.enumeration.ShipmentMode;
import com.cargotracker.domain.enumeration.PaymentMode;
import com.cargotracker.domain.enumeration.Status;
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

    private static final Boolean DEFAULT_IS_THIRD_PARTY = false;
    private static final Boolean UPDATED_IS_THIRD_PARTY = true;

    private static final CARRIER DEFAULT_CARRIER = CARRIER.DHL;
    private static final CARRIER UPDATED_CARRIER = CARRIER.FEDEX;

    private static final String DEFAULT_COURIER = "AAAAAAAAAA";
    private static final String UPDATED_COURIER = "BBBBBBBBBB";

    private static final String DEFAULT_CARRIER_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER_REF_NO = "BBBBBBBBBB";

    private static final ShipmentType DEFAULT_SHIPMENT_TYPE = ShipmentType.DOCUMENT;
    private static final ShipmentType UPDATED_SHIPMENT_TYPE = ShipmentType.PARCEL;

    private static final ShipmentMode DEFAULT_SHIPMENT_MODE = ShipmentMode.AIR;
    private static final ShipmentMode UPDATED_SHIPMENT_MODE = ShipmentMode.ROAD;

    private static final LocalDate DEFAULT_PICKUP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKUP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPECTED_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(2);

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final BigDecimal DEFAULT_TOTAL_FRIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_FRIGHT = new BigDecimal(2);

    private static final String DEFAULT_PACKAGE_DESCIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_DESCIPTION = "BBBBBBBBBB";

    private static final PaymentMode DEFAULT_PAYMENT_MODE = PaymentMode.TOPAY;
    private static final PaymentMode UPDATED_PAYMENT_MODE = PaymentMode.PAID;

    private static final Status DEFAULT_STATUS = Status.INTRANSIENT;
    private static final Status UPDATED_STATUS = Status.PENDING;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

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
            .isThirdParty(DEFAULT_IS_THIRD_PARTY)
            .carrier(DEFAULT_CARRIER)
            .courier(DEFAULT_COURIER)
            .carrierRefNo(DEFAULT_CARRIER_REF_NO)
            .shipmentType(DEFAULT_SHIPMENT_TYPE)
            .shipmentMode(DEFAULT_SHIPMENT_MODE)
            .pickupDate(DEFAULT_PICKUP_DATE)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .weight(DEFAULT_WEIGHT)
            .quantity(DEFAULT_QUANTITY)
            .totalFright(DEFAULT_TOTAL_FRIGHT)
            .packageDesciption(DEFAULT_PACKAGE_DESCIPTION)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS);
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
        assertThat(testShipmentInfo.isIsThirdParty()).isEqualTo(DEFAULT_IS_THIRD_PARTY);
        assertThat(testShipmentInfo.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testShipmentInfo.getCourier()).isEqualTo(DEFAULT_COURIER);
        assertThat(testShipmentInfo.getCarrierRefNo()).isEqualTo(DEFAULT_CARRIER_REF_NO);
        assertThat(testShipmentInfo.getShipmentType()).isEqualTo(DEFAULT_SHIPMENT_TYPE);
        assertThat(testShipmentInfo.getShipmentMode()).isEqualTo(DEFAULT_SHIPMENT_MODE);
        assertThat(testShipmentInfo.getPickupDate()).isEqualTo(DEFAULT_PICKUP_DATE);
        assertThat(testShipmentInfo.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testShipmentInfo.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testShipmentInfo.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShipmentInfo.getTotalFright()).isEqualTo(DEFAULT_TOTAL_FRIGHT);
        assertThat(testShipmentInfo.getPackageDesciption()).isEqualTo(DEFAULT_PACKAGE_DESCIPTION);
        assertThat(testShipmentInfo.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testShipmentInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShipmentInfo.getComments()).isEqualTo(DEFAULT_COMMENTS);

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
    public void checkShipmentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setShipmentType(null);

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
    public void checkShipmentModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setShipmentMode(null);

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
    public void checkPickupDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setPickupDate(null);

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
    public void checkPaymentModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setPaymentMode(null);

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
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentInfoRepository.findAll().size();
        // set the field null
        shipmentInfo.setStatus(null);

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
            .andExpect(jsonPath("$.[*].isThirdParty").value(hasItem(DEFAULT_IS_THIRD_PARTY.booleanValue())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].courier").value(hasItem(DEFAULT_COURIER.toString())))
            .andExpect(jsonPath("$.[*].carrierRefNo").value(hasItem(DEFAULT_CARRIER_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].shipmentType").value(hasItem(DEFAULT_SHIPMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shipmentMode").value(hasItem(DEFAULT_SHIPMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].pickupDate").value(hasItem(DEFAULT_PICKUP_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalFright").value(hasItem(DEFAULT_TOTAL_FRIGHT.intValue())))
            .andExpect(jsonPath("$.[*].packageDesciption").value(hasItem(DEFAULT_PACKAGE_DESCIPTION.toString())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
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
            .andExpect(jsonPath("$.isThirdParty").value(DEFAULT_IS_THIRD_PARTY.booleanValue()))
            .andExpect(jsonPath("$.carrier").value(DEFAULT_CARRIER.toString()))
            .andExpect(jsonPath("$.courier").value(DEFAULT_COURIER.toString()))
            .andExpect(jsonPath("$.carrierRefNo").value(DEFAULT_CARRIER_REF_NO.toString()))
            .andExpect(jsonPath("$.shipmentType").value(DEFAULT_SHIPMENT_TYPE.toString()))
            .andExpect(jsonPath("$.shipmentMode").value(DEFAULT_SHIPMENT_MODE.toString()))
            .andExpect(jsonPath("$.pickupDate").value(DEFAULT_PICKUP_DATE.toString()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalFright").value(DEFAULT_TOTAL_FRIGHT.intValue()))
            .andExpect(jsonPath("$.packageDesciption").value(DEFAULT_PACKAGE_DESCIPTION.toString()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
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
            .isThirdParty(UPDATED_IS_THIRD_PARTY)
            .carrier(UPDATED_CARRIER)
            .courier(UPDATED_COURIER)
            .carrierRefNo(UPDATED_CARRIER_REF_NO)
            .shipmentType(UPDATED_SHIPMENT_TYPE)
            .shipmentMode(UPDATED_SHIPMENT_MODE)
            .pickupDate(UPDATED_PICKUP_DATE)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .weight(UPDATED_WEIGHT)
            .quantity(UPDATED_QUANTITY)
            .totalFright(UPDATED_TOTAL_FRIGHT)
            .packageDesciption(UPDATED_PACKAGE_DESCIPTION)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);

        restShipmentInfoMockMvc.perform(put("/api/shipment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipmentInfo)))
            .andExpect(status().isOk());

        // Validate the ShipmentInfo in the database
        List<ShipmentInfo> shipmentInfoList = shipmentInfoRepository.findAll();
        assertThat(shipmentInfoList).hasSize(databaseSizeBeforeUpdate);
        ShipmentInfo testShipmentInfo = shipmentInfoList.get(shipmentInfoList.size() - 1);
        assertThat(testShipmentInfo.getConsignmentNo()).isEqualTo(UPDATED_CONSIGNMENT_NO);
        assertThat(testShipmentInfo.isIsThirdParty()).isEqualTo(UPDATED_IS_THIRD_PARTY);
        assertThat(testShipmentInfo.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testShipmentInfo.getCourier()).isEqualTo(UPDATED_COURIER);
        assertThat(testShipmentInfo.getCarrierRefNo()).isEqualTo(UPDATED_CARRIER_REF_NO);
        assertThat(testShipmentInfo.getShipmentType()).isEqualTo(UPDATED_SHIPMENT_TYPE);
        assertThat(testShipmentInfo.getShipmentMode()).isEqualTo(UPDATED_SHIPMENT_MODE);
        assertThat(testShipmentInfo.getPickupDate()).isEqualTo(UPDATED_PICKUP_DATE);
        assertThat(testShipmentInfo.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testShipmentInfo.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testShipmentInfo.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShipmentInfo.getTotalFright()).isEqualTo(UPDATED_TOTAL_FRIGHT);
        assertThat(testShipmentInfo.getPackageDesciption()).isEqualTo(UPDATED_PACKAGE_DESCIPTION);
        assertThat(testShipmentInfo.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testShipmentInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShipmentInfo.getComments()).isEqualTo(UPDATED_COMMENTS);

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
            .andExpect(jsonPath("$.[*].isThirdParty").value(hasItem(DEFAULT_IS_THIRD_PARTY.booleanValue())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].courier").value(hasItem(DEFAULT_COURIER)))
            .andExpect(jsonPath("$.[*].carrierRefNo").value(hasItem(DEFAULT_CARRIER_REF_NO)))
            .andExpect(jsonPath("$.[*].shipmentType").value(hasItem(DEFAULT_SHIPMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shipmentMode").value(hasItem(DEFAULT_SHIPMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].pickupDate").value(hasItem(DEFAULT_PICKUP_DATE.toString())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalFright").value(hasItem(DEFAULT_TOTAL_FRIGHT.intValue())))
            .andExpect(jsonPath("$.[*].packageDesciption").value(hasItem(DEFAULT_PACKAGE_DESCIPTION)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
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
