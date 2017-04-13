package com.pilpoil.repository;

import com.pilpoil.domain.Breed;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Breed entity.
 */
public interface BreedRepository extends JpaRepository<Breed,Long> {
	
	@Query(value = "SELECT breed.id, breed.label, breed.animal_type_id "
			+ "FROM breed as breed "
			+ "WHERE breed.animal_type_id = ?1 "
			+ "ORDER BY breed.label ASC", nativeQuery = true)
	List<Breed> findBreedsByIdAnimalType(Long id);
}
