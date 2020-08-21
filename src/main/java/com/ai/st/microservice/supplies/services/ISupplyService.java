package com.ai.st.microservice.supplies.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;

public interface ISupplyService {

	public SupplyEntity createSupply(SupplyEntity supplyEntity);

	public List<SupplyEntity> getSuppliesByMunicipalityCodeAndStates(String municipalityCode,
			List<SupplyStateEntity> states);

	public SupplyEntity getSupplyById(Long id);

	public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndStatesPaginated(String municipalityCode,
			List<SupplyStateEntity> states, int page, int numberItems);

	public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndRequestsAndStatesPaginated(String municipalityCode,
			List<Long> requests, List<SupplyStateEntity> states, int page, int numberItems);

	public void deleteSupplyById(Long id);

}
