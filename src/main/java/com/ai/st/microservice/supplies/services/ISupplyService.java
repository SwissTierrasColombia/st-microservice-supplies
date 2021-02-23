package com.ai.st.microservice.supplies.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;

public interface ISupplyService {

    SupplyEntity createSupply(SupplyEntity supplyEntity);

    List<SupplyEntity> getSuppliesByMunicipalityCodeAndStates(String municipalityCode, List<SupplyStateEntity> states);

    SupplyEntity getSupplyById(Long id);

    Page<SupplyEntity> getSuppliesByMunicipalityCodeAndStatesPaginated(String municipalityCode,
                                                                       Long managerCode,
                                                                       List<SupplyStateEntity> states, int page, int numberItems);

    Page<SupplyEntity> getSuppliesByMunicipalityCodeAndRequestsAndStatesPaginated(String municipalityCode,
                                                                                  List<Long> requests, List<SupplyStateEntity> states, int page, int numberItems);

    void deleteSupplyById(Long id);

    List<SupplyEntity> getSuppliesXTFByManager(Long managerCode, String municipalityCode);

}
