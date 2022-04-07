package com.ai.st.microservice.supplies.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(SupplyBusiness.class);

    public SupplyDto addSupplyToMunicipality(String municipalityCode, String observations, Long typeSupplyCode,
            Long managerCode, Long requestCode, List<CreateSupplyAttachmentDto> supplyAttachments,
            List<CreateSupplyOwnerDto> owners, String modelVersion, Long supplyStateId, String name, Boolean isValid)
            throws BusinessException {

        log.info("Add supply to municipality started");

        for (CreateSupplyAttachmentDto attachment : supplyAttachments) {
            System.out.println("attachment ID: " + attachment.getAttachmentTypeId());
            System.out.println("attachment DATA: " + attachment.getData());
        }

        final String supplyAttachmentsString = StringUtils.join(supplyAttachments.stream()
                .map(CreateSupplyAttachmentDto::getAttachmentTypeId).collect(Collectors.toList()), ",");
        final String ownersString = StringUtils
                .join(owners.stream().map(CreateSupplyOwnerDto::getOwnerCode).collect(Collectors.toList()), ",");

        log.info(String.format(
                "Params: municipality=%s observations=%s typeSupplyCode=%d managerCode=%d requestCode=%d supplyAttachments=%s "
                        + "owners=%s modelVersion=%s supplyStateId=%d name=%s isValid=%s",
                municipalityCode, observations, typeSupplyCode, managerCode, requestCode, supplyAttachmentsString,
                ownersString, modelVersion, supplyStateId, name, isValid));

        if (supplyAttachments.size() == 0) {
            throw new BusinessException("El insumo debe contener al menos un adjunto.");
        }

        // owners
        for (CreateSupplyOwnerDto owner : owners) {

            // verify type emitter
            if (!owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_MANAGER.name())
                    && !owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_PROVIDER.name())
                    && !owner.getOwnerType().equals(OwnerTypeEnum.USER.name())
                    && !owner.getOwnerType().equals(OwnerTypeEnum.CADASTRAL_AUTHORITY.name())) {
                throw new BusinessException("El tipo de propietario es inválido.");
            }

        }

        if (supplyStateId == null) {
            supplyStateId = SupplyStateBusiness.SUPPLY_STATE_ACTIVE;
        }

        SupplyStateEntity supplyState = supplyStateService.getSupplyStateById(supplyStateId);
        if (supplyState == null) {
            throw new BusinessException("El estado del insumo no existe");
        }

        SupplyEntity supplyEntity = new SupplyEntity();

        // attachments
        List<SupplyAttachmentEntity> attachments = new ArrayList<>();
        for (CreateSupplyAttachmentDto createAttachmentDto : supplyAttachments) {

            SupplyAttachmentTypeEntity attachmentType = attachmentTypeService
                    .getAttachmentTypeById(createAttachmentDto.getAttachmentTypeId());

            if (attachmentType == null) {
                throw new BusinessException("El tipo de adjunto no es válido.");
            }

            SupplyAttachmentEntity attachmentEntity = new SupplyAttachmentEntity();
            attachmentEntity.setCreatedAt(new Date());
            attachmentEntity.setSupply(supplyEntity);
            attachmentEntity.setData(createAttachmentDto.getData());
            attachmentEntity.setAttachmentType(attachmentType);
            attachments.add(attachmentEntity);
        }
        supplyEntity.setAttachments(attachments);

        log.info("Attachments validated");

        // owners
        List<SupplyOwnerEntity> ownersEntity = new ArrayList<>();
        for (CreateSupplyOwnerDto owner : owners) {

            SupplyOwnerEntity ownerEntity = new SupplyOwnerEntity();
            ownerEntity.setCreatedAt(new Date());
            ownerEntity.setOwnerCode(owner.getOwnerCode());

            OwnerTypeEnum ownerType;
            if (owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_MANAGER.name())) {
                ownerType = OwnerTypeEnum.ENTITY_MANAGER;
            } else if (owner.getOwnerType().equals(OwnerTypeEnum.ENTITY_PROVIDER.name())) {
                ownerType = OwnerTypeEnum.ENTITY_PROVIDER;
            } else if (owner.getOwnerType().equals(OwnerTypeEnum.CADASTRAL_AUTHORITY.name())) {
                ownerType = OwnerTypeEnum.CADASTRAL_AUTHORITY;
            } else {
                ownerType = OwnerTypeEnum.USER;
            }

            ownerEntity.setOwnerType(ownerType);
            ownerEntity.setSupply(supplyEntity);
            ownersEntity.add(ownerEntity);
        }
        supplyEntity.setOwners(ownersEntity);

        log.info("Owners validated");

        if (name != null) {
            supplyEntity.setName(name);
        }

        log.info("Name validated");

        supplyEntity.setCreatedAt(new Date());
        supplyEntity.setMunicipalityCode(municipalityCode);
        supplyEntity.setObservations(observations);
        supplyEntity.setState(supplyState);
        supplyEntity.setTypeSupplyCode(typeSupplyCode);
        supplyEntity.setRequestCode(requestCode);
        supplyEntity.setManagerCode(managerCode);
        if (isValid != null) {
            supplyEntity.setValid(isValid);
        }

        if (modelVersion != null && !modelVersion.isEmpty()) {
            supplyEntity.setModelVersion(modelVersion);
        }

        supplyEntity = supplyService.createSupply(supplyEntity);

        log.info("Supply created in database with id: " + supplyEntity.getId());

        return this.transformEntityToDto(supplyEntity);
    }

    public List<SupplyDto> getSuppliesXTFByMunicipality(String municipalityCode, Long managerCode) {

        List<SupplyEntity> supplyEntities = supplyService.getSuppliesXTFByManager(managerCode, municipalityCode);

        List<SupplyDto> supplyDtoList = new ArrayList<>();

        for (SupplyEntity supplyEntity : supplyEntities) {
            supplyDtoList.add(transformEntityToDto(supplyEntity));
        }

        return supplyDtoList;
    }

    public Object getSuppliesByMunicipality(String municipalityCode, Integer page, List<Long> requests,
            List<Long> states, Long managerCode) throws BusinessException {

        log.info("Get supplies by municipality business started");

        List<SupplyDto> suppliesDto = new ArrayList<>();

        List<SupplyEntity> suppliesEntity;
        Page<SupplyEntity> pageEntity = null;

        List<SupplyStateEntity> statesEntity = new ArrayList<>();

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

        final String statesStringId = StringUtils
                .join(statesEntity.stream().map(SupplyStateEntity::getId).collect(Collectors.toList()), ",");

        if (page == null) {

            log.info(String.format("Filter without pagination: municipality=%s states=%s", municipalityCode,
                    statesStringId));
            suppliesEntity = supplyService.getSuppliesByMunicipalityCodeAndStates(municipalityCode, statesEntity);

        } else {

            if (requests != null && requests.size() > 0) {

                log.info(String.format(
                        "Filter with pagination and requests: municipality=%s requests=%s states=%s page=%d perPage=%d",
                        municipalityCode, StringUtils.join(requests, ","), statesStringId, page, 10));

                pageEntity = supplyService.getSuppliesByMunicipalityCodeAndRequestsAndStatesPaginated(municipalityCode,
                        requests, statesEntity, page - 1, 10);
            } else {

                log.info(String.format(
                        "Filter with pagination but without requests: municipality=%s managerCode=%d states=%s page=%d perPage=%d",
                        municipalityCode, managerCode, statesStringId, page - 1, 10));

                pageEntity = supplyService.getSuppliesByMunicipalityCodeAndStatesPaginated(municipalityCode,
                        managerCode, statesEntity, page - 1, 10);
            }

            suppliesEntity = pageEntity.toList();
        }

        log.info("Supplies found " + suppliesEntity.size());

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

            log.info("result data" + dataPaginatedDto);

            return dataPaginatedDto;
        }

        return suppliesDto;
    }

    public SupplyDto getSupplyById(Long supplyId) throws BusinessException {

        SupplyDto supplyDto = null;

        SupplyEntity supplyEntity = supplyService.getSupplyById(supplyId);
        if (supplyEntity != null) {
            supplyDto = this.transformEntityToDto(supplyEntity);
        }

        return supplyDto;
    }

    public void deleteSupplyById(Long supplyId) throws BusinessException {

        SupplyEntity supplyEntity = supplyService.getSupplyById(supplyId);
        if (supplyEntity == null) {
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
        if (supplyEntity == null) {
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

        return this.transformEntityToDto(supplyEntity);
    }

    protected SupplyDto transformEntityToDto(SupplyEntity supplyEntity) {

        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(supplyEntity.getId());
        supplyDto.setName(supplyEntity.getName());
        supplyDto.setCreatedAt(supplyEntity.getCreatedAt());
        supplyDto.setMunicipalityCode(supplyEntity.getMunicipalityCode());
        supplyDto.setObservations(supplyEntity.getObservations());
        supplyDto.setState(new SupplyStateDto(supplyEntity.getState().getId(), supplyEntity.getState().getName()));
        supplyDto.setTypeSupplyCode(supplyEntity.getTypeSupplyCode());
        supplyDto.setRequestCode(supplyEntity.getRequestCode());
        supplyDto.setModelVersion(supplyEntity.getModelVersion());
        supplyDto.setManagerCode(supplyEntity.getManagerCode());
        supplyDto.setValid(supplyEntity.getValid());

        List<SupplyOwnerDto> ownersDto = new ArrayList<>();
        for (SupplyOwnerEntity ownerEntity : supplyEntity.getOwners()) {
            SupplyOwnerDto ownerDto = new SupplyOwnerDto();
            ownerDto.setCreatedAt(ownerEntity.getCreatedAt());
            ownerDto.setId(ownerEntity.getId());
            ownerDto.setOwnerCode(ownerEntity.getOwnerCode());
            ownerDto.setOwnerType(ownerEntity.getOwnerType().name());
            ownersDto.add(ownerDto);
        }
        supplyDto.setOwners(ownersDto);

        List<SupplyAttachmentDto> attachmentsDto = new ArrayList<>();
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
