package com.ai.st.microservice.supplies.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.supplies.dto.CreateSupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.SupplyAttachmentDto;
import com.ai.st.microservice.supplies.dto.SupplyDto;
import com.ai.st.microservice.supplies.dto.SupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.SupplyStateDto;
import com.ai.st.microservice.supplies.entities.OwnerTypeEnum;
import com.ai.st.microservice.supplies.entities.SupplyAttachmentEntity;
import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyOwnerEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.services.ISupplyService;
import com.ai.st.microservice.supplies.services.ISupplyStateService;

@Component
public class SupplyBusiness {

	@Autowired
	private ISupplyStateService supplyStateService;

	@Autowired
	private ISupplyService supplyService;

	public SupplyDto addSupplyToMunicipality(String municipalityCode, String observations, Long typeSupplyCode,
			String url, List<String> urlsDocumentaryRepository, List<CreateSupplyOwnerDto> owners)
			throws BusinessException {

		if (urlsDocumentaryRepository.size() == 0 && url.isEmpty()) {
			throw new BusinessException("El insumo debe contener un archivo o una url.");
		}

		// owners
		for (CreateSupplyOwnerDto owner : owners) {

			// verify type emitter
			if (!owner.getOwnerType().equals(OwnerTypeEnum.ENTITY.name())
					&& !owner.getOwnerType().equals(OwnerTypeEnum.USER.name())) {
				throw new BusinessException("El tipo de propietario es inválido.");
			}

		}

		SupplyStateEntity supplyState = supplyStateService.getSupplyStateById(SupplyStateBusiness.SUPPLY_STATE_ACTIVE);

		SupplyEntity supplyEntity = new SupplyEntity();

		// attachments
		List<SupplyAttachmentEntity> attachments = new ArrayList<SupplyAttachmentEntity>();
		if (urlsDocumentaryRepository.size() > 0) {
			for (String urlDocumentaryRepository : urlsDocumentaryRepository) {
				SupplyAttachmentEntity attachementEntity = new SupplyAttachmentEntity();
				attachementEntity.setCreatedAt(new Date());
				attachementEntity.setSupply(supplyEntity);
				attachementEntity.setUrlDocumentaryRepository(urlDocumentaryRepository);
				attachments.add(attachementEntity);
			}
		}
		supplyEntity.setAttachments(attachments);

		// owners
		List<SupplyOwnerEntity> ownersEntity = new ArrayList<SupplyOwnerEntity>();
		for (CreateSupplyOwnerDto owner : owners) {
			SupplyOwnerEntity ownerEntity = new SupplyOwnerEntity();
			ownerEntity.setCreatedAt(new Date());
			ownerEntity.setOwnerCode(owner.getOwnerCode());
			OwnerTypeEnum ownerType = (owner.getOwnerType().equals(OwnerTypeEnum.ENTITY.name())) ? OwnerTypeEnum.ENTITY
					: OwnerTypeEnum.USER;
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
		supplyEntity.setUrl(url);

		supplyEntity = supplyService.createSupply(supplyEntity);

		SupplyDto supplyDto = new SupplyDto();
		supplyDto.setId(supplyEntity.getId());
		supplyDto.setCreatedAt(supplyEntity.getCreatedAt());
		supplyDto.setMunicipalityCode(supplyEntity.getMunicipalityCode());
		supplyDto.setObservations(supplyEntity.getObservations());
		supplyDto.setUrl(supplyEntity.getUrl());
		supplyDto.setState(new SupplyStateDto(supplyEntity.getState().getId(), supplyEntity.getState().getName()));
		supplyDto.setTypeSupplyCode(supplyEntity.getTypeSupplyCode());

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
			attachmentDto.setUrlDocumentaryRepository(attachmentEntity.getUrlDocumentaryRepository());
			attachmentsDto.add(attachmentDto);
		}
		supplyDto.setAttachments(attachmentsDto);

		return supplyDto;
	}

	public List<SupplyDto> getSuppliesByMunicipality(String municipalityCode) throws BusinessException {

		List<SupplyDto> suppliesDto = new ArrayList<>();

		List<SupplyEntity> suppliesEntity = supplyService.getSuppliesByMunicipalityCode(municipalityCode);

		if (suppliesEntity.size() > 0) {

			for (SupplyEntity supplyEntity : suppliesEntity) {

				SupplyDto supplyDto = new SupplyDto();
				supplyDto.setId(supplyEntity.getId());
				supplyDto.setCreatedAt(supplyEntity.getCreatedAt());
				supplyDto.setMunicipalityCode(supplyEntity.getMunicipalityCode());
				supplyDto.setObservations(supplyEntity.getObservations());
				supplyDto.setUrl(supplyEntity.getUrl());
				supplyDto.setState(
						new SupplyStateDto(supplyEntity.getState().getId(), supplyEntity.getState().getName()));
				supplyDto.setTypeSupplyCode(supplyEntity.getTypeSupplyCode());

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
					attachmentDto.setUrlDocumentaryRepository(attachmentEntity.getUrlDocumentaryRepository());
					attachmentsDto.add(attachmentDto);
				}
				supplyDto.setAttachments(attachmentsDto);

				suppliesDto.add(supplyDto);
			}

		}

		return suppliesDto;
	}

}
