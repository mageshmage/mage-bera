package com.cargotracker.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of StateSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StateSearchRepositoryMockConfiguration {

    @MockBean
    private StateSearchRepository mockStateSearchRepository;

}
