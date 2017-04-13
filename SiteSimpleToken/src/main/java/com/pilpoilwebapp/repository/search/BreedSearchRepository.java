package com.pilpoilwebapp.repository.search;

import com.pilpoilwebapp.domain.Breed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Breed entity.
 */
public interface BreedSearchRepository extends ElasticsearchRepository<Breed, Long> {
}
