package com.ai.st.microservice.supplies.services;

import java.util.List;

import com.ai.st.microservice.supplies.entities.SupplyEntity;

public interface ISupplyService {

	public SupplyEntity createSupply(SupplyEntity supplyEntity);

	public List<SupplyEntity> getSuppliesByMunicipalityCode(String municipalityCode);

}
