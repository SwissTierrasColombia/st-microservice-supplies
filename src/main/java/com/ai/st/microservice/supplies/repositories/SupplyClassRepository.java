package com.ai.st.microservice.supplies.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ai.st.microservice.supplies.entities.SupplyClassEntity;

public interface SupplyClassRepository extends CrudRepository<SupplyClassEntity, Long> {

}
