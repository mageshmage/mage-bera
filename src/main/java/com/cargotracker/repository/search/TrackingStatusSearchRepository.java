package com.cargotracker.repository.search;

import com.cargotracker.domain.TrackingStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TrackingStatus entity.
 */
public interface TrackingStatusSearchRepository extends ElasticsearchRepository<TrackingStatus, Long> {
}
