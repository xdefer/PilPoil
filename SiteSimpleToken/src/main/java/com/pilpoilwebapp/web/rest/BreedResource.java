package com.pilpoilwebapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoilwebapp.domain.Breed;
import com.pilpoilwebapp.repository.BreedRepository;
import com.pilpoilwebapp.repository.search.BreedSearchRepository;
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
 * REST controller for managing Breed.
 */
@RestController
@RequestMapping("/api")
public class BreedResource {

    private final Logger log = LoggerFactory.getLogger(BreedResource.class);
        
    @Inject
    private BreedRepository breedRepository;
    
    @Inject
    private BreedSearchRepository breedSearchRepository;
    
    /**
     * POST  /breeds -> Create a new breed.
     */
    @RequestMapping(value = "/breeds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Breed> createBreed(@RequestBody Breed breed) throws URISyntaxException {
        log.debug("REST request to save Breed : {}", breed);
        if (breed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("breed", "idexists", "A new breed cannot already have an ID")).body(null);
        }
        Breed result = breedRepository.save(breed);
        breedSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/breeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("breed", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /breeds -> Updates an existing breed.
     */
    @RequestMapping(value = "/breeds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Breed> updateBreed(@RequestBody Breed breed) throws URISyntaxException {
        log.debug("REST request to update Breed : {}", breed);
        if (breed.getId() == null) {
            return createBreed(breed);
        }
        Breed result = breedRepository.save(breed);
        breedSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("breed", breed.getId().toString()))
            .body(result);
    }

    /**
     * GET  /breeds -> get all the breeds.
     */
    @RequestMapping(value = "/breeds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Breed> getAllBreeds() {
        log.debug("REST request to get all Breeds");
        return breedRepository.findAll();
            }

    /**
     * GET  /breeds/:id -> get the "id" breed.
     */
    @RequestMapping(value = "/breeds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Breed> getBreed(@PathVariable Long id) {
        log.debug("REST request to get Breed : {}", id);
        Breed breed = breedRepository.findOne(id);
        return Optional.ofNullable(breed)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /breeds/:id -> delete the "id" breed.
     */
    @RequestMapping(value = "/breeds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBreed(@PathVariable Long id) {
        log.debug("REST request to delete Breed : {}", id);
        breedRepository.delete(id);
        breedSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("breed", id.toString())).build();
    }

    /**
     * SEARCH  /_search/breeds/:query -> search for the breed corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/breeds/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Breed> searchBreeds(@PathVariable String query) {
        log.debug("REST request to search Breeds for query {}", query);
        return StreamSupport
            .stream(breedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * GET  /breeds/animalType/:id -> get breeds by id animalType
     */
    @RequestMapping(value = "/breeds/animalType/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Breed> getBreedsByIdAnimalType(@PathVariable Long id) {
        log.debug("REST request to get Race by id type animal : {}", id);
        return breedRepository.findBreedsByIdAnimalType(id);
    }
}
