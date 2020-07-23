package com.ai.st.microservice.supplies.services;

import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;

public interface ISupplyAttachmentTypeService {

	public SupplyAttachmentTypeEntity createAttachmentType(SupplyAttachmentTypeEntity entity);

	public SupplyAttachmentTypeEntity getAttachmentTypeById(Long id);

	public Long getCount();

}
