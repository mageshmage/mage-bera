package com.cargotracker.repository.search;

import com.cargotracker.domain.PaymentMode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaymentMode entity.
 */
public interface PaymentModeSearchRepository extends ElasticsearchRepository<PaymentMode, Long> {
}
