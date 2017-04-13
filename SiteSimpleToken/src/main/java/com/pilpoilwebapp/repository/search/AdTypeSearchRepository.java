package com.pilpoilwebapp.repository.search;

import com.pilpoilwebapp.domain.AdType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AdType entity.
 */
public interface AdTypeSearchRepository extends ElasticsearchRepository<AdType, Long> {
}
