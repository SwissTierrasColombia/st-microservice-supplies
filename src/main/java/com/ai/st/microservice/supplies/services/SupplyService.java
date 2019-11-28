package com.ai.st.microservice.supplies.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.repositories.SupplyRepository;

@Service
public class SupplyService implements ISupplyService {

	@Autowired
	private SupplyRepository supplyRepository;

	@Override
	@Transactional
	public SupplyEntity createSupply(SupplyEntity supplyEntity) {
		return supplyRepository.save(supplyEntity);
	}

}
