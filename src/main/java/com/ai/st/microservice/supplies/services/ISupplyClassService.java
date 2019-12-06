package com.ai.st.microservice.supplies.services;

import com.ai.st.microservice.supplies.entities.SupplyClassEntity;

public interface ISupplyClassService {

	public Long getCount();

	public SupplyClassEntity createSupplyClass(SupplyClassEntity supplyClassEntity);

}
