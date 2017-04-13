package com.pilpoilwebapp.web.rest;

import com.pilpoilwebapp.Application;
import com.pilpoilwebapp.domain.AnimalType;
import com.pilpoilwebapp.repository.AnimalTypeRepository;
import com.pilpoilwebapp.repository.search.AnimalTypeSearchRepository;

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
 * Test class for the AnimalTypeResource REST controller.
 *
 * @see AnimalTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnimalTypeResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    @Inject
    private AnimalTypeRepository animalTypeRepository;

    @Inject
    private AnimalTypeSearchRepository animalTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAnimalTypeMockMvc;

    private AnimalType animalType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnimalTypeResource animalTypeResource = new AnimalTypeResource();
        ReflectionTestUtils.setField(animalTypeResource, "animalTypeSearchRepository", animalTypeSearchRepository);
        ReflectionTestUtils.setField(animalTypeResource, "animalTypeRepository", animalTypeRepository);
        this.restAnimalTypeMockMvc = MockMvcBuilders.standaloneSetup(animalTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        animalType = new AnimalType();
        animalType.setLabel(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createAnimalType() throws Exception {
        int databaseSizeBeforeCreate = animalTypeRepository.findAll().size();

        // Create the AnimalType

        restAnimalTypeMockMvc.perform(post("/api/animalTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animalType)))
                .andExpect(status().isCreated());

        // Validate the AnimalType in the database
        List<AnimalType> animalTypes = animalTypeRepository.findAll();
        assertThat(animalTypes).hasSize(databaseSizeBeforeCreate + 1);
        AnimalType testAnimalType = animalTypes.get(animalTypes.size() - 1);
        assertThat(testAnimalType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void getAllAnimalTypes() throws Exception {
        // Initialize the database
        animalTypeRepository.saveAndFlush(animalType);

        // Get all the animalTypes
        restAnimalTypeMockMvc.perform(get("/api/animalTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(animalType.getId().intValue())))
                .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getAnimalType() throws Exception {
        // Initialize the database
        animalTypeRepository.saveAndFlush(animalType);

        // Get the animalType
        restAnimalTypeMockMvc.perform(get("/api/animalTypes/{id}", animalType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(animalType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimalType() throws Exception {
        // Get the animalType
        restAnimalTypeMockMvc.perform(get("/api/animalTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalType() throws Exception {
        // Initialize the database
        animalTypeRepository.saveAndFlush(animalType);

		int databaseSizeBeforeUpdate = animalTypeRepository.findAll().size();

        // Update the animalType
        animalType.setLabel(UPDATED_LABEL);

        restAnimalTypeMockMvc.perform(put("/api/animalTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animalType)))
                .andExpect(status().isOk());

        // Validate the AnimalType in the database
        List<AnimalType> animalTypes = animalTypeRepository.findAll();
        assertThat(animalTypes).hasSize(databaseSizeBeforeUpdate);
        AnimalType testAnimalType = animalTypes.get(animalTypes.size() - 1);
        assertThat(testAnimalType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void deleteAnimalType() throws Exception {
        // Initialize the database
        animalTypeRepository.saveAndFlush(animalType);

		int databaseSizeBeforeDelete = animalTypeRepository.findAll().size();

        // Get the animalType
        restAnimalTypeMockMvc.perform(delete("/api/animalTypes/{id}", animalType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AnimalType> animalTypes = animalTypeRepository.findAll();
        assertThat(animalTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
