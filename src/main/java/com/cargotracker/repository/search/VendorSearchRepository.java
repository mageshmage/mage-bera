package com.cargotracker.repository.search;

import com.cargotracker.domain.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Vendor entity.
 */
public interface VendorSearchRepository extends ElasticsearchRepository<Vendor, Long> {
}
