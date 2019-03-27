package com.cargotracker.repository.search;

import com.cargotracker.domain.ShipmentInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShipmentInfo entity.
 */
public interface ShipmentInfoSearchRepository extends ElasticsearchRepository<ShipmentInfo, Long> {
}
