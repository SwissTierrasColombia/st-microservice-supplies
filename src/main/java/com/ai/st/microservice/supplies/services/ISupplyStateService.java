package com.ai.st.microservice.supplies.services;

import com.ai.st.microservice.supplies.entities.SupplyStateEntity;

public interface ISupplyStateService {

	public Long getCount();

	public SupplyStateEntity createSupplyState(SupplyStateEntity supplyStateEntity);

	public SupplyStateEntity getSupplyStateById(Long id);

}
