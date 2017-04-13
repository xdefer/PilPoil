package com.pilpoilwebapp.web.rest;

import com.pilpoilwebapp.Application;
import com.pilpoilwebapp.domain.Ad;
import com.pilpoilwebapp.repository.AdRepository;
import com.pilpoilwebapp.repository.search.AdSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AdResource REST controller.
 *
 * @see AdResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_RECOVER = false;
    private static final Boolean UPDATED_RECOVER = true;
    private static final String DEFAULT_DATE = "AAAAA";
    private static final String UPDATED_DATE = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ADRESS = "AAAAA";
    private static final String UPDATED_ADRESS = "BBBBB";
    private static final String DEFAULT_COMPLEMENT = "AAAAA";
    private static final String UPDATED_COMPLEMENT = "BBBBB";
    private static final String DEFAULT_POSTAL_CODE = "AAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_LONGITUDE = "AAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBB";
    private static final String DEFAULT_LATTITUDE = "AAAAA";
    private static final String UPDATED_LATTITUDE = "BBBBB";

    @Inject
    private AdRepository adRepository;

    @Inject
    private AdSearchRepository adSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdMockMvc;

    private Ad ad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdResource adResource = new AdResource();
        ReflectionTestUtils.setField(adResource, "adSearchRepository", adSearchRepository);
        ReflectionTestUtils.setField(adResource, "adRepository", adRepository);
        this.restAdMockMvc = MockMvcBuilders.standaloneSetup(adResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ad = new Ad();
        ad.setDescription(DEFAULT_DESCRIPTION);
        ad.setRecover(DEFAULT_RECOVER);
        ad.setDate(DEFAULT_DATE);
        ad.setPhone(DEFAULT_PHONE);
        ad.setEmail(DEFAULT_EMAIL);
        ad.setAdress(DEFAULT_ADRESS);
        ad.setComplement(DEFAULT_COMPLEMENT);
        ad.setPostalCode(DEFAULT_POSTAL_CODE);
        ad.setCity(DEFAULT_CITY);
        ad.setCountry(DEFAULT_COUNTRY);
        ad.setLongitude(DEFAULT_LONGITUDE);
        ad.setLattitude(DEFAULT_LATTITUDE);
    }

    @Test
    @Transactional
    public void createAd() throws Exception {
        int databaseSizeBeforeCreate = adRepository.findAll().size();

        // Create the Ad

        restAdMockMvc.perform(post("/api/ads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ad)))
                .andExpect(status().isCreated());

        // Validate the Ad in the database
        List<Ad> ads = adRepository.findAll();
        assertThat(ads).hasSize(databaseSizeBeforeCreate + 1);
        Ad testAd = ads.get(ads.size() - 1);
        assertThat(testAd.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAd.getRecover()).isEqualTo(DEFAULT_RECOVER);
        assertThat(testAd.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAd.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAd.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAd.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testAd.getComplement()).isEqualTo(DEFAULT_COMPLEMENT);
        assertThat(testAd.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAd.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAd.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAd.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAd.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
    }

    @Test
    @Transactional
    public void getAllAds() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the ads
        restAdMockMvc.perform(get("/api/ads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].recover").value(hasItem(DEFAULT_RECOVER.booleanValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
                .andExpect(jsonPath("$.[*].complement").value(hasItem(DEFAULT_COMPLEMENT.toString())))
                .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
                .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE.toString())));
    }

    @Test
    @Transactional
    public void getAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", ad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ad.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.recover").value(DEFAULT_RECOVER.booleanValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.complement").value(DEFAULT_COMPLEMENT.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.lattitude").value(DEFAULT_LATTITUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAd() throws Exception {
        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

		int databaseSizeBeforeUpdate = adRepository.findAll().size();

        // Update the ad
        ad.setDescription(UPDATED_DESCRIPTION);
        ad.setRecover(UPDATED_RECOVER);
        ad.setDate(UPDATED_DATE);
        ad.setPhone(UPDATED_PHONE);
        ad.setEmail(UPDATED_EMAIL);
        ad.setAdress(UPDATED_ADRESS);
        ad.setComplement(UPDATED_COMPLEMENT);
        ad.setPostalCode(UPDATED_POSTAL_CODE);
        ad.setCity(UPDATED_CITY);
        ad.setCountry(UPDATED_COUNTRY);
        ad.setLongitude(UPDATED_LONGITUDE);
        ad.setLattitude(UPDATED_LATTITUDE);

        restAdMockMvc.perform(put("/api/ads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ad)))
                .andExpect(status().isOk());

        // Validate the Ad in the database
        List<Ad> ads = adRepository.findAll();
        assertThat(ads).hasSize(databaseSizeBeforeUpdate);
        Ad testAd = ads.get(ads.size() - 1);
        assertThat(testAd.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAd.getRecover()).isEqualTo(UPDATED_RECOVER);
        assertThat(testAd.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAd.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAd.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAd.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testAd.getComplement()).isEqualTo(UPDATED_COMPLEMENT);
        assertThat(testAd.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAd.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAd.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAd.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAd.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
    }

    @Test
    @Transactional
    public void deleteAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

		int databaseSizeBeforeDelete = adRepository.findAll().size();

        // Get the ad
        restAdMockMvc.perform(delete("/api/ads/{id}", ad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ad> ads = adRepository.findAll();
        assertThat(ads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
