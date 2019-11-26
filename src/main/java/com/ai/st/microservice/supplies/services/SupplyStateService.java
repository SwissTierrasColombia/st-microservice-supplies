package com.ai.st.microservice.supplies.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.supplies.entities.SupplyStateEntity;
import com.ai.st.microservice.supplies.repositories.SupplyStateRepository;

@Service
public class SupplyStateService implements ISupplyStateService {

	@Autowired
	private SupplyStateRepository supplyStateRepository;

	@Override
	public Long getCount() {
		return supplyStateRepository.count();
	}

	@Override
	@Transactional
	public SupplyStateEntity createSupplyState(SupplyStateEntity supplyStateEntity) {
		return supplyStateRepository.save(supplyStateEntity);
	}

}
