package com.pilpoil.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoil.domain.Breed;
import com.pilpoil.repository.BreedRepository;
import com.pilpoil.web.rest.util.HeaderUtil;
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

/**
 * REST controller for managing Breed.
 */
@RestController
@RequestMapping("/api")
public class BreedResource {

    private final Logger log = LoggerFactory.getLogger(BreedResource.class);
        
    @Inject
    private BreedRepository breedRepository;
    
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
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("breed", id.toString())).build();
    }
}
