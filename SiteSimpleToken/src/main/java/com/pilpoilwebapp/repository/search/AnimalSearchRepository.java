package com.pilpoilwebapp.repository.search;

import com.pilpoilwebapp.domain.Animal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Animal entity.
 */
public interface AnimalSearchRepository extends ElasticsearchRepository<Animal, Long> {
}
