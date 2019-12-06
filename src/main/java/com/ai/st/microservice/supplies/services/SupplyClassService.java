package com.ai.st.microservice.supplies.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.supplies.entities.SupplyClassEntity;
import com.ai.st.microservice.supplies.repositories.SupplyClassRepository;

@Service
public class SupplyClassService implements ISupplyClassService {

	@Autowired
	private SupplyClassRepository supplyClassRepository;

	@Override
	public Long getCount() {
		return supplyClassRepository.count();
	}

	@Override
	@Transactional
	public SupplyClassEntity createSupplyClass(SupplyClassEntity supplyClassEntity) {
		return supplyClassRepository.save(supplyClassEntity);
	}

}
