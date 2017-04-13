package com.pilpoil.web.rest;

import com.pilpoil.Application;
import com.pilpoil.domain.Animal;
import com.pilpoil.repository.AnimalRepository;

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
 * Test class for the AnimalResource REST controller.
 *
 * @see AnimalResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AnimalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_AGE = "AAAAA";
    private static final String UPDATED_AGE = "BBBBB";
    private static final String DEFAULT_SEXE = "AAAAA";
    private static final String UPDATED_SEXE = "BBBBB";
    private static final String DEFAULT_TATOO = "AAAAA";
    private static final String UPDATED_TATOO = "BBBBB";
    private static final String DEFAULT_CHIP = "AAAAA";
    private static final String UPDATED_CHIP = "BBBBB";
    private static final String DEFAULT_PHOTO = "AAAAA";
    private static final String UPDATED_PHOTO = "BBBBB";
    private static final String DEFAULT_COLORS = "AAAAA";
    private static final String UPDATED_COLORS = "BBBBB";

    @Inject
    private AnimalRepository animalRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAnimalMockMvc;

    private Animal animal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnimalResource animalResource = new AnimalResource();
        ReflectionTestUtils.setField(animalResource, "animalRepository", animalRepository);
        this.restAnimalMockMvc = MockMvcBuilders.standaloneSetup(animalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        animal = new Animal();
        animal.setName(DEFAULT_NAME);
        animal.setAge(DEFAULT_AGE);
        animal.setSexe(DEFAULT_SEXE);
        animal.setTatoo(DEFAULT_TATOO);
        animal.setChip(DEFAULT_CHIP);
        animal.setPhoto(DEFAULT_PHOTO);
        animal.setColors(DEFAULT_COLORS);
    }

    @Test
    @Transactional
    public void createAnimal() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal

        restAnimalMockMvc.perform(post("/api/animals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animal)))
                .andExpect(status().isCreated());

        // Validate the Animal in the database
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(databaseSizeBeforeCreate + 1);
        Animal testAnimal = animals.get(animals.size() - 1);
        assertThat(testAnimal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnimal.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testAnimal.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAnimal.getTatoo()).isEqualTo(DEFAULT_TATOO);
        assertThat(testAnimal.getChip()).isEqualTo(DEFAULT_CHIP);
        assertThat(testAnimal.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testAnimal.getColors()).isEqualTo(DEFAULT_COLORS);
    }

    @Test
    @Transactional
    public void getAllAnimals() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animals
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())))
                .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
                .andExpect(jsonPath("$.[*].tatoo").value(hasItem(DEFAULT_TATOO.toString())))
                .andExpect(jsonPath("$.[*].chip").value(hasItem(DEFAULT_CHIP.toString())))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].colors").value(hasItem(DEFAULT_COLORS.toString())));
    }

    @Test
    @Transactional
    public void getAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(animal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.tatoo").value(DEFAULT_TATOO.toString()))
            .andExpect(jsonPath("$.chip").value(DEFAULT_CHIP.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.colors").value(DEFAULT_COLORS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimal() throws Exception {
        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

		int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Update the animal
        animal.setName(UPDATED_NAME);
        animal.setAge(UPDATED_AGE);
        animal.setSexe(UPDATED_SEXE);
        animal.setTatoo(UPDATED_TATOO);
        animal.setChip(UPDATED_CHIP);
        animal.setPhoto(UPDATED_PHOTO);
        animal.setColors(UPDATED_COLORS);

        restAnimalMockMvc.perform(put("/api/animals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(animal)))
                .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(databaseSizeBeforeUpdate);
        Animal testAnimal = animals.get(animals.size() - 1);
        assertThat(testAnimal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnimal.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAnimal.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAnimal.getTatoo()).isEqualTo(UPDATED_TATOO);
        assertThat(testAnimal.getChip()).isEqualTo(UPDATED_CHIP);
        assertThat(testAnimal.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testAnimal.getColors()).isEqualTo(UPDATED_COLORS);
    }

    @Test
    @Transactional
    public void deleteAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

		int databaseSizeBeforeDelete = animalRepository.findAll().size();

        // Get the animal
        restAnimalMockMvc.perform(delete("/api/animals/{id}", animal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Animal> animals = animalRepository.findAll();
        assertThat(animals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
