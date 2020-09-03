package com.ai.st.microservice.supplies.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.supplies.dto.SupplyAttachmentTypeDto;
import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.services.ISupplyAttachmentTypeService;

@Component
public class SupplyAttachmentTypeBusiness {

	public static final Long SUPPLY_ATTACHMENT_TYPE_SUPPLY = (long) 1;
	public static final Long SUPPLY_ATTACHMENT_TYPE_FTP = (long) 2;
	public static final Long SUPPLY_ATTACHMENT_TYPE_EXTERNAL_SOURCE = (long) 3;

	@Autowired
	private ISupplyAttachmentTypeService attachmentTypeService;

	public List<SupplyAttachmentTypeDto> getAttachmentsTypes() throws BusinessException {

		List<SupplyAttachmentTypeDto> attachmentsTypesDto = new ArrayList<>();

		List<SupplyAttachmentTypeEntity> attachmentsTypesEntity = attachmentTypeService.getSupplyAttachmentsTypes();

		for (SupplyAttachmentTypeEntity attachmentEntity : attachmentsTypesEntity) {
			attachmentsTypesDto.add(new SupplyAttachmentTypeDto(attachmentEntity.getId(), attachmentEntity.getName()));
		}

		return attachmentsTypesDto;
	}

}
