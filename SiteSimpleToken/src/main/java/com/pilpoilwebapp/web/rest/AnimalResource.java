package com.pilpoilwebapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoilwebapp.domain.Ad;
import com.pilpoilwebapp.domain.Animal;
import com.pilpoilwebapp.repository.AdRepository;
import com.pilpoilwebapp.repository.AnimalRepository;
import com.pilpoilwebapp.repository.search.AdSearchRepository;
import com.pilpoilwebapp.repository.search.AnimalSearchRepository;
import com.pilpoilwebapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Animal.
 */
@RestController
@RequestMapping("/api")
public class AnimalResource {

    private final Logger log = LoggerFactory.getLogger(AnimalResource.class);
        
    @Inject
    private AnimalRepository animalRepository;
    
    @Inject
    private AnimalSearchRepository animalSearchRepository;
    
    @Inject
    private AdRepository adRepository;
    
    @Inject
    private AdSearchRepository adSearchRepository;
    
    /**
     * POST  /animals -> Create a new animal.
     */
    @RequestMapping(value = "/animals",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) throws URISyntaxException {
        log.debug("REST request to save Animal : {}", animal);
        if (animal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("animal", "idexists", "A new animal cannot already have an ID")).body(null);
        }
        Animal result = animalRepository.save(animal);
        animalSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("animal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /animals -> Updates an existing animal.
     */
    @RequestMapping(value = "/animals",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Animal> updateAnimal(@RequestBody Animal animal) throws URISyntaxException {
        log.debug("REST request to update Animal : {}", animal);
        if (animal.getId() == null) {
            return createAnimal(animal);
        }
        Animal result = animalRepository.save(animal);
        animalSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("animal", animal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /animals -> get all the animals.
     */
    @RequestMapping(value = "/animals",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Animal> getAllAnimals() {
        log.debug("REST request to get all Animals");
        return animalRepository.findAll();
            }

    /**
     * GET  /animals/:id -> get the "id" animal.
     */
    @RequestMapping(value = "/animals/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        log.debug("REST request to get Animal : {}", id);
        Animal animal = animalRepository.findOne(id);
        return Optional.ofNullable(animal)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /animals/:id -> delete the "id" animal.
     */
    @RequestMapping(value = "/animals/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        log.debug("REST request to delete Animal : {}", id);
        List<Ad> ads = adRepository.findByAnimalId(id);
        if(!ads.isEmpty()){
        	for(Ad ad: ads){
        		adRepository.delete(ad.getId());
        		adSearchRepository.delete(ad.getId());
        	}
        }
        animalRepository.delete(id);
        animalSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("animal", id.toString())).build();
    }

    /**
     * SEARCH  /_search/animals/:query -> search for the animal corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/animals/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Animal> searchAnimals(@PathVariable String query) {
        log.debug("REST request to search Animals for query {}", query);
        return StreamSupport
            .stream(animalSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * GET  /animals/user/{id} -> get all the animals of a user.
     */
    @RequestMapping(value = "/animals/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Animal> getAllAnimalsByUserId(@PathVariable Long id) {
        log.debug("REST request to get all Animals by user id : {]", id);
        return animalRepository.findAllByUserId(id);
    }
    
    /**
     * GET  /animals/user/{id} -> get all the animals not Lost of a user.
     */
	@RequestMapping(value = "/notLostAnimals/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ArrayList<Animal> getAllNotLostAnimalsByUserId(@PathVariable Long id) {
        log.debug("REST request to get all Animals by user id : {]", id);
        List<BigInteger> animalLstId = animalRepository.findAllNotLostByUserId(id);
        ArrayList<Animal> animalLstReturn = new ArrayList<Animal>();
        for(BigInteger idAnimal : animalLstId){
        	animalLstReturn.add(animalRepository.findOneWithEagerRelationships(idAnimal.longValue()));
        }
        return animalLstReturn;
    }
}
