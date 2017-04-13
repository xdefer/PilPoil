package com.pilpoilwebapp.repository;

import com.pilpoilwebapp.domain.AnimalType;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AnimalType entity.
 */
public interface AnimalTypeRepository extends JpaRepository<AnimalType,Long> {
	
    @Query("select distinct animalType from AnimalType animalType left join fetch animalType.breeds")
    List<AnimalType> findAllWithEagerRelationships();

    @Query("select animalType from AnimalType animalType left join fetch animalType.breeds where animalType.id =:id")
    AnimalType findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select distinct animalType from AnimalType animalType left join fetch animalType.breeds "
    		+ "Order by CASE animalType.label WHEN 'Chat' THEN 1 WHEN 'Chien' THEN 2 WHEN 'Autre' THEN 99 ELSE 3 END, animalType.label")
    List<AnimalType> getAllWithSpecificOrderBy();
}
