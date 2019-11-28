package com.ai.st.microservice.supplies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.supplies.entities.SupplyEntity;

public interface SupplyRepository extends CrudRepository<SupplyEntity, Long> {

}
