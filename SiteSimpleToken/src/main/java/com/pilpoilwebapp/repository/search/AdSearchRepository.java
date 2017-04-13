package com.pilpoilwebapp.repository.search;

import com.pilpoilwebapp.domain.Ad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Ad entity.
 */
public interface AdSearchRepository extends ElasticsearchRepository<Ad, Long> {
}
