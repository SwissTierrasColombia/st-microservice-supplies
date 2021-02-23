package com.ai.st.microservice.supplies.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;
import com.ai.st.microservice.supplies.repositories.SupplyRepository;

@Service
public class SupplyService implements ISupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Override
    @Transactional
    public SupplyEntity createSupply(SupplyEntity supplyEntity) {
        return supplyRepository.save(supplyEntity);
    }

    @Override
    public List<SupplyEntity> getSuppliesByMunicipalityCodeAndStates(String municipalityCode,
                                                                     List<SupplyStateEntity> states) {
        return supplyRepository.findByMunicipalityCodeAndStateIn(municipalityCode, states);
    }

    @Override
    public SupplyEntity getSupplyById(Long id) {
        return supplyRepository.findById(id).orElse(null);
    }

    @Override
    public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndStatesPaginated(String municipalityCode,
                                                                              Long managerCode,
                                                                              List<SupplyStateEntity> states, int page, int numberItems) {
        Pageable pageable = PageRequest.of(page, numberItems);
        return supplyRepository.findByMunicipalityCodeAndStateInAndManagerCode(municipalityCode, states, managerCode, pageable);
    }

    @Override
    public Page<SupplyEntity> getSuppliesByMunicipalityCodeAndRequestsAndStatesPaginated(String municipalityCode,
                                                                                         List<Long> requests, List<SupplyStateEntity> states, int page, int numberItems) {
        Pageable pageable = PageRequest.of(page, numberItems);
        return supplyRepository.findByMunicipalityCodeAndRequestCodeInAndStateIn(municipalityCode, requests, states,
                pageable);
    }

    @Override
    @Transactional
    public void deleteSupplyById(Long id) {
        supplyRepository.deleteById(id);
    }

    @Override
    public List<SupplyEntity> getSuppliesXTFByManager(Long managerCode, String municipalityCode) {
        return supplyRepository.findByManagerCodeAndModelVersionIsNotNullAndMunicipalityCode(managerCode, municipalityCode);
    }

}
