package com.pilpoilwebapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoilwebapp.domain.Ad;
import com.pilpoilwebapp.domain.AdType;
import com.pilpoilwebapp.repository.AdTypeRepository;
import com.pilpoilwebapp.repository.search.AdTypeSearchRepository;
import com.pilpoilwebapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AdType.
 */
@RestController
@RequestMapping("/api")
public class AdTypeResource {

    private final Logger log = LoggerFactory.getLogger(AdTypeResource.class);
        
    @Inject
    private AdTypeRepository adTypeRepository;
    
    @Inject
    private AdTypeSearchRepository adTypeSearchRepository;
    
    /**
     * POST  /adTypes -> Create a new adType.
     */
    @RequestMapping(value = "/adTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdType> createAdType(@RequestBody AdType adType) throws URISyntaxException {
        log.debug("REST request to save AdType : {}", adType);
        if (adType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("adType", "idexists", "A new adType cannot already have an ID")).body(null);
        }
        AdType result = adTypeRepository.save(adType);
        adTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/adTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("adType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adTypes -> Updates an existing adType.
     */
    @RequestMapping(value = "/adTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdType> updateAdType(@RequestBody AdType adType) throws URISyntaxException {
        log.debug("REST request to update AdType : {}", adType);
        if (adType.getId() == null) {
            return createAdType(adType);
        }
        AdType result = adTypeRepository.save(adType);
        adTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("adType", adType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adTypes -> get all the adTypes.
     */
    @RequestMapping(value = "/adTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AdType> getAllAdTypes() {
        log.debug("REST request to get all AdTypes");
        return adTypeRepository.findAll();
            }

    /**
     * GET  /adTypes/:id -> get the "id" adType.
     */
    @RequestMapping(value = "/adTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdType> getAdType(@PathVariable Long id) {
        log.debug("REST request to get AdType : {}", id);
        AdType adType = adTypeRepository.findOne(id);
        return Optional.ofNullable(adType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /adTypes/:id -> delete the "id" adType.
     */
    @RequestMapping(value = "/adTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdType(@PathVariable Long id) {
        log.debug("REST request to delete AdType : {}", id);
        adTypeRepository.delete(id);
        adTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/adTypes/:query -> search for the adType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/adTypes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AdType> searchAdTypes(@PathVariable String query) {
        log.debug("REST request to search AdTypes for query {}", query);
        return StreamSupport
            .stream(adTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
