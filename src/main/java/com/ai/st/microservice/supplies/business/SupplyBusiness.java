package com.ai.st.microservice.supplies.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.supplies.dto.CreateSupplyAttachmentDto;
import com.ai.st.microservice.supplies.dto.CreateSupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.DataPaginatedDto;
import com.ai.st.microservice.supplies.dto.SupplyAttachmentDto;
import com.ai.st.microservice.supplies.dto.SupplyAttachmentTypeDto;
import com.ai.st.microservice.supplies.dto.SupplyDto;
import com.ai.st.microservice.supplies.dto.SupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.SupplyStateDto;
import com.ai.st.microservice.supplies.entities.OwnerTypeEnum;
import com.ai.st.microservice.supplies.entities.SupplyAttachmentEntity;
import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;
import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyOwnerEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.services.ISupplyAttachmentTypeService;
import com.ai.st.microservice.supplies.services.ISupplyService;
import com.ai.st.microservice.supplies.services.ISupplyStateService;

@Component
public class SupplyBusiness {

	@Autowired
	private ISupplyStateService supplyStateService;

	@Autowired
	private ISupplyService supplyService;

	@Autowired
	private ISupplyAttachmentTypeService attachmentTypeService;

	public SupplyDto addSupplyToMunicipality(String municipalityCode, String observations, Long typeSupplyCode,
			Long requestCode, List<CreateSupplyAttachmentDto> supplyAttachments, List<CreateSupplyOwnerDto> owners,
			String modelVersion) throws BusinessException {

		if (supplyAttachments.size() == 0) {
			throw new BusinessException("El insumo debe contener al menos un adjunto.");
		}

		// owners
		for (CreateSupplyOwnerDto owner : owners) {

			// verify type emitter
			if (!owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_MANAGER.name())
					&& !owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_PROVIDER.name())
					&& !owner.getOwnerType().equals(OwnerTypeEnum.USER.name())) {
				throw new BusinessException("El tipo de propietario es inválido.");
			}

		}

		SupplyStateEntity supplyState = supplyStateService.getSupplyStateById(SupplyStateBusiness.SUPPLY_STATE_ACTIVE);

		SupplyEntity supplyEntity = new SupplyEntity();

		// attachments
		List<SupplyAttachmentEntity> attachments = new ArrayList<SupplyAttachmentEntity>();
		for (CreateSupplyAttachmentDto createAttachmentDto : supplyAttachments) {

			SupplyAttachmentTypeEntity attachmentType = attachmentTypeService
					.getAttachmentTypeById(createAttachmentDto.getAttachmentTypeId());

			if (attachmentType == null) {
				throw new BusinessException("El tipo de adjunto no es válido.");
			}

			SupplyAttachmentEntity attachementEntity = new SupplyAttachmentEntity();
			attachementEntity.setCreatedAt(new Date());
			attachementEntity.setSupply(supplyEntity);
			attachementEntity.setData(createAttachmentDto.getData());
			attachementEntity.setAttachmentType(attachmentType);
			attachments.add(attachementEntity);
		}
		supplyEntity.setAttachments(attachments);

		// owners
		List<SupplyOwnerEntity> ownersEntity = new ArrayList<SupplyOwnerEntity>();
		for (CreateSupplyOwnerDto owner : owners) {

			SupplyOwnerEntity ownerEntity = new SupplyOwnerEntity();
			ownerEntity.setCreatedAt(new Date());
			ownerEntity.setOwnerCode(owner.getOwnerCode());

			OwnerTypeEnum ownerType = null;
			if (owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_MANAGER.name())) {
				ownerType = OwnerTypeEnum.ENTITY_MANAGER;
			} else if (owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_PROVIDER.name())) {
				ownerType = OwnerTypeEnum.ENTITY_PROVIDER;
			} else {
				ownerType = OwnerTypeEnum.USER;
			}

			ownerEntity.setOwnerType(ownerType);
			ownerEntity.setSupply(supplyEntity);
			ownersEntity.add(ownerEntity);
		}
		supplyEntity.setOwners(ownersEntity);

		supplyEntity.setCreatedAt(new Date());
		supplyEntity.setMunicipalityCode(municipalityCode);
		supplyEntity.setObservations(observations);
		supplyEntity.setState(supplyState);
		supplyEntity.setTypeSupplyCode(typeSupplyCode);
		supplyEntity.setRequestCode(requestCode);

		if (modelVersion != null && !modelVersion.isEmpty()) {
			supplyEntity.setModelVersion(modelVersion);
		}

		supplyEntity = supplyService.createSupply(supplyEntity);

		SupplyDto supplyDto = this.transformEntityToDto(supplyEntity);

		return supplyDto;
	}

	public Object getSuppliesByMunicipality(String municipalityCode, Integer page, List<Long> requests,
			List<Long> states) throws BusinessException {

		List<SupplyDto> suppliesDto = new ArrayList<>();

		List<SupplyEntity> suppliesEntity = new ArrayList<>();
		Page<SupplyEntity> pageEntity = null;

		List<SupplyStateEntity> statesEntity = new ArrayList<SupplyStateEntity>();

		if (states == null) {

			SupplyStateEntity stateActive = supplyStateService
					.getSupplyStateById(SupplyStateBusiness.SUPPLY_STATE_ACTIVE);
			SupplyStateEntity stateInactive = supplyStateService
					.getSupplyStateById(SupplyStateBusiness.SUPPLY_STATE_INACTIVE);
			SupplyStateEntity stateRemoved = supplyStateService
					.getSupplyStateById(SupplyStateBusiness.SUPPLY_STATE_REMOVED);

			statesEntity.add(stateActive);
			statesEntity.add(stateInactive);
			statesEntity.add(stateRemoved);

		} else {
			for (Long stateId : states) {
				SupplyStateEntity stateEntity = supplyStateService.getSupplyStateById(stateId);
				if (stateEntity != null) {
					statesEntity.add(stateEntity);
				}
			}
		}

		if (page == null) {
			suppliesEntity = supplyService.getSuppliesByMunicipalityCodeAndStates(municipalityCode, statesEntity);
		} else {

			if (requests != null && requests.size() > 0) {
				pageEntity = supplyService.getSuppliesByMunicipalityCodeAndRequestsAndStatesPaginated(municipalityCode,
						requests, statesEntity, page - 1, 10);
				suppliesEntity = pageEntity.toList();
			} else {
				pageEntity = supplyService.getSuppliesByMunicipalityCodeAndStatesPaginated(municipalityCode,
						statesEntity, page - 1, 10);
				suppliesEntity = pageEntity.toList();
			}
		}

		for (SupplyEntity supplyEntity : suppliesEntity) {
			SupplyDto supplyDto = this.transformEntityToDto(supplyEntity);
			suppliesDto.add(supplyDto);
		}

		if (page != null) {
			DataPaginatedDto dataPaginatedDto = new DataPaginatedDto();
			dataPaginatedDto.setNumber(pageEntity.getNumber());
			dataPaginatedDto.setItems(suppliesDto);
			dataPaginatedDto.setNumberOfElements(pageEntity.getNumberOfElements());
			dataPaginatedDto.setTotalElements(pageEntity.getTotalElements());
			dataPaginatedDto.setTotalPages(pageEntity.getTotalPages());
			dataPaginatedDto.setSize(pageEntity.getSize());
			return dataPaginatedDto;
		}

		return suppliesDto;
	}

	public SupplyDto getSupplyById(Long supplyId) throws BusinessException {

		SupplyDto supplyDto = null;

		SupplyEntity supplyEntity = supplyService.getSupplyById(supplyId);
		if (supplyEntity instanceof SupplyEntity) {
			supplyDto = this.transformEntityToDto(supplyEntity);
		}

		return supplyDto;
	}

	public void deleteSupplyById(Long supplyId) throws BusinessException {

		SupplyEntity supplyEntity = supplyService.getSupplyById(supplyId);
		if (!(supplyEntity instanceof SupplyEntity)) {
			throw new BusinessException("No se ha encontrado el insumo.");
		}

		try {

			supplyService.deleteSupplyById(supplyId);

		} catch (Exception e) {
			throw new BusinessException("No se ha podido eliminar el insumo.");
		}

	}

	public SupplyDto updateSupply(Long supplyId, Long stateId) throws BusinessException {

		SupplyEntity supplyEntity = supplyService.getSupplyById(supplyId);
		if (!(supplyEntity instanceof SupplyEntity)) {
			throw new BusinessException("No se ha encontrado el insumo.");
		}

		if (stateId != null) {
			SupplyStateEntity stateEntity = supplyStateService.getSupplyStateById(stateId);
			if (stateEntity == null) {
				throw new BusinessException("El estado del insumo no existe.");
			}
			supplyEntity.setState(stateEntity);
		}

		supplyEntity = supplyService.createSupply(supplyEntity);

		SupplyDto supplyDto = this.transformEntityToDto(supplyEntity);

		return supplyDto;
	}

	protected SupplyDto transformEntityToDto(SupplyEntity supplyEntity) {

		SupplyDto supplyDto = new SupplyDto();
		supplyDto.setId(supplyEntity.getId());
		supplyDto.setCreatedAt(supplyEntity.getCreatedAt());
		supplyDto.setMunicipalityCode(supplyEntity.getMunicipalityCode());
		supplyDto.setObservations(supplyEntity.getObservations());
		supplyDto.setState(new SupplyStateDto(supplyEntity.getState().getId(), supplyEntity.getState().getName()));
		supplyDto.setTypeSupplyCode(supplyEntity.getTypeSupplyCode());
		supplyDto.setRequestCode(supplyEntity.getRequestCode());
		supplyDto.setModelVersion(supplyEntity.getModelVersion());

		List<SupplyOwnerDto> ownersDto = new ArrayList<SupplyOwnerDto>();
		for (SupplyOwnerEntity ownerEntity : supplyEntity.getOwners()) {
			SupplyOwnerDto ownerDto = new SupplyOwnerDto();
			ownerDto.setCreatedAt(ownerEntity.getCreatedAt());
			ownerDto.setId(ownerEntity.getId());
			ownerDto.setOwnerCode(ownerEntity.getOwnerCode());
			ownerDto.setOwnerType(ownerEntity.getOwnerType().name());
			ownersDto.add(ownerDto);
		}
		supplyDto.setOwners(ownersDto);

		List<SupplyAttachmentDto> attachmentsDto = new ArrayList<SupplyAttachmentDto>();
		for (SupplyAttachmentEntity attachmentEntity : supplyEntity.getAttachments()) {
			SupplyAttachmentDto attachmentDto = new SupplyAttachmentDto();
			attachmentDto.setCreatedAt(attachmentEntity.getCreatedAt());
			attachmentDto.setId(attachmentEntity.getId());
			attachmentDto.setData(attachmentEntity.getData());
			SupplyAttachmentTypeEntity attachmentTypeEntity = attachmentEntity.getAttachmentType();
			attachmentDto.setAttachmentType(
					new SupplyAttachmentTypeDto(attachmentTypeEntity.getId(), attachmentTypeEntity.getName()));
			attachmentsDto.add(attachmentDto);
		}
		supplyDto.setAttachments(attachmentsDto);

		return supplyDto;
	}

}
