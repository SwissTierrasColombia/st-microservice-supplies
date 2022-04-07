package com.ai.st.microservice.supplies.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;
import com.ai.st.microservice.supplies.models.repositories.SupplyAttachmentTypeRepository;

@Service
public class SupplyAttachmentTypeService implements ISupplyAttachmentTypeService {

    @Autowired
    private SupplyAttachmentTypeRepository attachmentTypeRepository;

    @Override
    @Transactional
    public SupplyAttachmentTypeEntity createAttachmentType(SupplyAttachmentTypeEntity entity) {
        return attachmentTypeRepository.save(entity);
    }

    @Override
    public SupplyAttachmentTypeEntity getAttachmentTypeById(Long id) {
        return attachmentTypeRepository.findById(id).orElse(null);
    }

    @Override
    public Long getCount() {
        return attachmentTypeRepository.count();
    }

    @Override
    public List<SupplyAttachmentTypeEntity> getSupplyAttachmentsTypes() {
        return attachmentTypeRepository.findAll();
    }

}
