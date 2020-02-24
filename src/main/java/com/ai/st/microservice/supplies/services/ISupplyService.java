package com.ai.st.microservice.supplies.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ai.st.microservice.supplies.entities.SupplyEntity;

public interface ISupplyService {

	public SupplyEntity createSupply(SupplyEntity supplyEntity);

	public List<SupplyEntity> getSuppliesByMunicipalityCode(String municipalityCode);

	public SupplyEntity getSupplyById(Long id);

	public Page<SupplyEntity> getSuppliesByMunicipalityCodePaginated(String municipalityCode, int page,
			int numberItems);

	public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndRequestsPaginated(String municipalityCode,
			List<Long> requests, int page, int numberItems);

	public void deleteSupplyById(Long id);

}
