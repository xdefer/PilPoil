package com.pilpoilwebapp.repository;

import com.pilpoilwebapp.domain.Animal;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * Spring Data JPA repository for the Animal entity.
 */
public interface AnimalRepository extends JpaRepository<Animal,Long> {

	 @Query("select distinct animal from Animal animal")
	 List<Animal> findAllWithEagerRelationships();

	 @Query("select animal from Animal animal where animal.id =:id")
	 Animal findOneWithEagerRelationships(@Param("id") Long id);

	 @Query("select distinct animal from Animal animal where animal.user.id =:id order by animal.name ASC")
	 List<Animal> findAllByUserId(@Param("id")Long id);
	    
	 @Query(value = "SELECT animal.id FROM animal as animal WHERE animal.user_id = :id AND animal.id NOT IN "
			 + "(SELECT ad.animal_id FROM ad as ad INNER JOIN ad_type as adType ON ad.ad_type_id = adType.id "
			 + "INNER JOIN animal ON ad.animal_id = animal.id WHERE adType.label like 'Perdu' AND animal.user_id = :id )"
	    	, nativeQuery = true)
	 List<BigInteger> findAllNotLostByUserId(@Param("id")Long id);
}
