package com.ai.st.microservice.supplies.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.supplies.entities.SupplyEntity;

public interface SupplyRepository extends CrudRepository<SupplyEntity, Long> {

	List<SupplyEntity> findByMunicipalityCode(String municipalityCode);

}
