package com.pilpoilwebapp.web.rest;

import com.pilpoilwebapp.Application;
import com.pilpoilwebapp.domain.AdType;
import com.pilpoilwebapp.repository.AdTypeRepository;
import com.pilpoilwebapp.repository.search.AdTypeSearchRepository;

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
 * Test class for the AdTypeResource REST controller.
 *
 * @see AdTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdTypeResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private AdTypeRepository adTypeRepository;

    @Inject
    private AdTypeSearchRepository adTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdTypeMockMvc;

    private AdType adType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdTypeResource adTypeResource = new AdTypeResource();
        ReflectionTestUtils.setField(adTypeResource, "adTypeSearchRepository", adTypeSearchRepository);
        ReflectionTestUtils.setField(adTypeResource, "adTypeRepository", adTypeRepository);
        this.restAdTypeMockMvc = MockMvcBuilders.standaloneSetup(adTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        adType = new AdType();
        adType.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createAdType() throws Exception {
        int databaseSizeBeforeCreate = adTypeRepository.findAll().size();

        // Create the AdType

        restAdTypeMockMvc.perform(post("/api/adTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adType)))
                .andExpect(status().isCreated());

        // Validate the AdType in the database
        List<AdType> adTypes = adTypeRepository.findAll();
        assertThat(adTypes).hasSize(databaseSizeBeforeCreate + 1);
        AdType testAdType = adTypes.get(adTypes.size() - 1);
        assertThat(testAdType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllAdTypes() throws Exception {
        // Initialize the database
        adTypeRepository.saveAndFlush(adType);

        // Get all the adTypes
        restAdTypeMockMvc.perform(get("/api/adTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(adType.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getAdType() throws Exception {
        // Initialize the database
        adTypeRepository.saveAndFlush(adType);

        // Get the adType
        restAdTypeMockMvc.perform(get("/api/adTypes/{id}", adType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(adType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdType() throws Exception {
        // Get the adType
        restAdTypeMockMvc.perform(get("/api/adTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdType() throws Exception {
        // Initialize the database
        adTypeRepository.saveAndFlush(adType);

		int databaseSizeBeforeUpdate = adTypeRepository.findAll().size();

        // Update the adType
        adType.setLabel(UPDATED_LABEL);

        restAdTypeMockMvc.perform(put("/api/adTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(adType)))
                .andExpect(status().isOk());

        // Validate the AdType in the database
        List<AdType> adTypes = adTypeRepository.findAll();
        assertThat(adTypes).hasSize(databaseSizeBeforeUpdate);
        AdType testAdType = adTypes.get(adTypes.size() - 1);
        assertThat(testAdType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteAdType() throws Exception {
        // Initialize the database
        adTypeRepository.saveAndFlush(adType);

		int databaseSizeBeforeDelete = adTypeRepository.findAll().size();

        // Get the adType
        restAdTypeMockMvc.perform(delete("/api/adTypes/{id}", adType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AdType> adTypes = adTypeRepository.findAll();
        assertThat(adTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
