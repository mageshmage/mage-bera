package com.cargotracker.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ShipmentTypeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ShipmentTypeSearchRepositoryMockConfiguration {

    @MockBean
    private ShipmentTypeSearchRepository mockShipmentTypeSearchRepository;

}
