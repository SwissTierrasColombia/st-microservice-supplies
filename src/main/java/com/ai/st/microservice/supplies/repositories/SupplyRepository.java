package com.ai.st.microservice.supplies.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ai.st.microservice.supplies.entities.SupplyEntity;

public interface SupplyRepository extends PagingAndSortingRepository<SupplyEntity, Long> {

	List<SupplyEntity> findByMunicipalityCode(String municipalityCode);

	Page<SupplyEntity> findByMunicipalityCode(String municipalityCode, Pageable pageable);

	Page<SupplyEntity> findByMunicipalityCodeAndRequestCodeIn(String municipalityCode, List<Long> requests,
			Pageable pageable);

}
