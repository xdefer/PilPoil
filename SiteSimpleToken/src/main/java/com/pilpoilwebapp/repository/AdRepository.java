package com.pilpoilwebapp.repository;

import com.pilpoilwebapp.domain.Ad;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ad entity.
 */
public interface AdRepository extends JpaRepository<Ad,Long> {
	
	@Query(value = "SELECT ad.* "
			+ "FROM ad as ad "
			+ "INNER JOIN animal as animal ON ad.animal_id = animal.id "
			+ "INNER JOIN animal_type as animalType ON animal.animal_type_id = animalType.id "
			+ "INNER JOIN ad_type as adType ON ad.ad_type_id = adType.id "
			+ "WHERE animalType.id = :id "
			+ "AND adType.label like 'Trouvé' "
			// TODO : Ad date verification
			+ "AND STR_TO_DATE(ad.date, '%d/%m/%Y') > CURDATE() - INTERVAL 30 DAY"
			// + "AND parsedatetime(ad.date, 'dd/MM/yyy') > dateADD('DAY',-30, CURRENT_date)" // JPA
			, nativeQuery = true)
	List<Ad> findAllAdsByIdAnimalType(@Param("id") Long id);
	
	@Query(value = "SELECT ad.* "
			+ "FROM ad as ad "
			+ "INNER JOIN animal as animal ON ad.animal_id = animal.id "
			+ "INNER JOIN ad_type as adType ON ad.ad_type_id = adType.id "
			+ "WHERE animal.id = :id "
			+ "AND adType.label like 'Perdu' ", nativeQuery = true)
	List<Ad> findAllLostAdsByIdAnimal(@Param("id") Long id);
	
	List<Ad> findByAnimalId(Long id);
	
}
