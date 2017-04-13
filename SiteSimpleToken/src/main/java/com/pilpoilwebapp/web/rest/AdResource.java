package com.pilpoilwebapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pilpoilwebapp.domain.Ad;
import com.pilpoilwebapp.domain.AdType;
import com.pilpoilwebapp.domain.User;
import com.pilpoilwebapp.repository.AdRepository;
import com.pilpoilwebapp.repository.AdTypeRepository;
import com.pilpoilwebapp.repository.UserRepository;
import com.pilpoilwebapp.repository.search.AdSearchRepository;
import com.pilpoilwebapp.service.MailService;
import com.pilpoilwebapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Ad.
 */
@RestController
@RequestMapping("/api")
public class AdResource {

    private final Logger log = LoggerFactory.getLogger(AdResource.class);
    
    private final long AD_TYPE_ARCHIVE = 3;
        
    @Inject
    private AdRepository adRepository;
    
    @Inject
    private AdTypeRepository adTypeRepository;
    
    @Inject
    private AdSearchRepository adSearchRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private MailService mailService;
    
    /**
     * POST  /ads -> Create a new ad.
     */
    @RequestMapping(value = "/ads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ad> createAd(@RequestBody Ad ad) throws URISyntaxException {
        log.debug("REST request to save Ad : {}", ad);
        if (ad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ad", "idexists", "A new ad cannot already have an ID")).body(null);
        }
        Ad result = adRepository.save(ad);
        adSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ad", result.getId().toString()))
            .body(result);
    }
    
    /**
     * POST  /ads/report/{id} -> report existing ad
     */
    @RequestMapping(value = "/ads/report/{id}/{reason}",
        method = RequestMethod.POST)
    @Timed
    public ResponseEntity<?> reportAd(@PathVariable Long id, @PathVariable String reason, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST ad to report : {}", id);
        String login = request.getUserPrincipal().getName();
        Optional<User> optionalUser = userRepository.findOneByLogin(login);
        Ad adToReport = adRepository.findOne(id);
        if(optionalUser.isPresent() && adToReport != null){
        	User user = optionalUser.get();
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" +
            		request.getServerPort() + request.getContextPath();
            mailService.sendReportMail(adToReport, reason, user, baseUrl);
            return new ResponseEntity<>("e-mail send", HttpStatus.OK);
        }
        return new ResponseEntity<>("error user or ad do not exist", HttpStatus.BAD_REQUEST);
    }

    /**
     * PUT  /ads -> Updates an existing ad.
     */
    @RequestMapping(value = "/ads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ad> updateAd(@RequestBody Ad ad) throws URISyntaxException {
        log.debug("REST request to update Ad : {}", ad);
        if (ad.getId() == null) {
            return createAd(ad);
        }
        Ad result = adRepository.save(ad);
        adSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ad", ad.getId().toString()))
            .body(result);
    }
   
    
    /**
     * PUT  /ads/archive -> Updates an existing ad to be archive
     */
    @RequestMapping(value = "/ads/archive/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateAdArchive(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to archive Ad : {}", id);
        Ad adToArchive = adRepository.findOne(id);
        AdType adTypeArchive = adTypeRepository.findOne(AD_TYPE_ARCHIVE);
       	adToArchive.setAdType(adTypeArchive);
       	Ad result = adRepository.save(adToArchive);
       	adSearchRepository.save(result);
	    return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("ad", id.toString())).build();
    }

    /**
     * GET  /ads -> get all the ads.
     */
    @RequestMapping(value = "/ads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ad> getAllAds() {
        log.debug("REST request to get all Ads");
        return adRepository.findAll();
            }

    /**
     * GET  /ads/:id -> get the "id" ad.
     */
    @RequestMapping(value = "/ads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ad> getAd(@PathVariable Long id) {
        log.debug("REST request to get Ad : {}", id);
        Ad ad = adRepository.findOne(id);
        return Optional.ofNullable(ad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ads/:id -> delete the "id" ad.
     */
    @RequestMapping(value = "/ads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        log.debug("REST request to delete Ad : {}", id);
        adRepository.delete(id);
        adSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ad", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ads/:query -> search for the ad corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/ads/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ad> searchAds(@PathVariable String query) {
        log.debug("REST request to search Ads for query {}", query);
        return StreamSupport
            .stream(adSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * GET  /ads/animalType/{id} -> get all the ads by animalType.
     */
    @RequestMapping(value = "/ads/animalType/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ad> getAllAdsByIdAnimalType(@PathVariable Long id) {
        log.debug("REST request to get all ads by id animalType : {}", id);
        return adRepository.findAllAdsByIdAnimalType(id);
    }
    
    /**
     * GET  /ads/animal/{id} -> get all the ads by animalId.
     */
    @RequestMapping(value = "/ads/animal/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ad> getAllLostAdsByIdAnimal(@PathVariable Long id) {
        log.debug("REST request to get all ads by id animalType : {}", id);
        return adRepository.findAllLostAdsByIdAnimal(id);
    }
}
