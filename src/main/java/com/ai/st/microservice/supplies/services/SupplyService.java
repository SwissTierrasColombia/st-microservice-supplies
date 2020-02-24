package com.ai.st.microservice.supplies.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public List<SupplyEntity> getSuppliesByMunicipalityCode(String municipalityCode) {
		return supplyRepository.findByMunicipalityCode(municipalityCode);
	}

	@Override
	public SupplyEntity getSupplyById(Long id) {
		return supplyRepository.findById(id).orElse(null);
	}

	@Override
	public Page<SupplyEntity> getSuppliesByMunicipalityCodePaginated(String municipalityCode, int page,
			int numberItems) {
		Pageable pageable = PageRequest.of(page, numberItems);
		return supplyRepository.findByMunicipalityCode(municipalityCode, pageable);
	}

	@Override
	public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndRequestsPaginated(String municipalityCode,
			List<Long> requests, int page, int numberItems) {
		Pageable pageable = PageRequest.of(page, numberItems);
		return supplyRepository.findByMunicipalityCodeAndRequestCodeIn(municipalityCode, requests, pageable);
	}

	@Override
	@Transactional
	public void deleteSupplyById(Long id) {
		supplyRepository.deleteById(id);
	}

}
