package com.ai.st.microservice.supplies.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;

public interface SupplyAttachmentTypeRepository extends CrudRepository<SupplyAttachmentTypeEntity, Long> {

	@Override
	List<SupplyAttachmentTypeEntity> findAll();
	
}
