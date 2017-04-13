package com.pilpoilwebapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoilwebapp.domain.AnimalType;
import com.pilpoilwebapp.repository.AnimalTypeRepository;
import com.pilpoilwebapp.repository.search.AnimalTypeSearchRepository;
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
 * REST controller for managing AnimalType.
 */
@RestController
@RequestMapping("/api")
public class AnimalTypeResource {

    private final Logger log = LoggerFactory.getLogger(AnimalTypeResource.class);
        
    @Inject
    private AnimalTypeRepository animalTypeRepository;
    
    @Inject
    private AnimalTypeSearchRepository animalTypeSearchRepository;
    
    /**
     * POST  /animalTypes -> Create a new animalType.
     */
    @RequestMapping(value = "/animalTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnimalType> createAnimalType(@RequestBody AnimalType animalType) throws URISyntaxException {
        log.debug("REST request to save AnimalType : {}", animalType);
        if (animalType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("animalType", "idexists", "A new animalType cannot already have an ID")).body(null);
        }
        AnimalType result = animalTypeRepository.save(animalType);
        animalTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/animalTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("animalType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /animalTypes -> Updates an existing animalType.
     */
    @RequestMapping(value = "/animalTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnimalType> updateAnimalType(@RequestBody AnimalType animalType) throws URISyntaxException {
        log.debug("REST request to update AnimalType : {}", animalType);
        if (animalType.getId() == null) {
            return createAnimalType(animalType);
        }
        AnimalType result = animalTypeRepository.save(animalType);
        animalTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("animalType", animalType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /animalTypes -> get all the animalTypes.
     */
    @RequestMapping(value = "/animalTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AnimalType> getAllAnimalTypes() {
        log.debug("REST request to get all AnimalTypes");
        return animalTypeRepository.findAll();
            }
    
    /**
     * GET  /animalTypes -> get all the animalTypes.
     */
    @RequestMapping(value = "/animalTypesWithSpecificOrderBy",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AnimalType> getAllWithSpecificOrderBy() {
        log.debug("REST request to get all AnimalTypes");
        return animalTypeRepository.getAllWithSpecificOrderBy();
    }

    /**
     * GET  /animalTypes/:id -> get the "id" animalType.
     */
    @RequestMapping(value = "/animalTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AnimalType> getAnimalType(@PathVariable Long id) {
        log.debug("REST request to get AnimalType : {}", id);
        AnimalType animalType = animalTypeRepository.findOne(id);
        return Optional.ofNullable(animalType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /animalTypes/:id -> delete the "id" animalType.
     */
    @RequestMapping(value = "/animalTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAnimalType(@PathVariable Long id) {
        log.debug("REST request to delete AnimalType : {}", id);
        animalTypeRepository.delete(id);
        animalTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("animalType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/animalTypes/:query -> search for the animalType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/animalTypes/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AnimalType> searchAnimalTypes(@PathVariable String query) {
        log.debug("REST request to search AnimalTypes for query {}", query);
        return StreamSupport
            .stream(animalTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
