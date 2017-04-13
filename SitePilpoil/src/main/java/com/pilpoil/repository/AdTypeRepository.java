package com.pilpoil.repository;

import com.pilpoil.domain.AdType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AdType entity.
 */
public interface AdTypeRepository extends JpaRepository<AdType,Long> {

}
