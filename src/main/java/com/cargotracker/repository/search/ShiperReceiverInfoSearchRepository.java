package com.cargotracker.repository.search;

import com.cargotracker.domain.ShiperReceiverInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShiperReceiverInfo entity.
 */
public interface ShiperReceiverInfoSearchRepository extends ElasticsearchRepository<ShiperReceiverInfo, Long> {
}
