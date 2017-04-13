package com.pilpoilwebapp.repository.search;

import com.pilpoilwebapp.domain.AnimalType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AnimalType entity.
 */
public interface AnimalTypeSearchRepository extends ElasticsearchRepository<AnimalType, Long> {
}
