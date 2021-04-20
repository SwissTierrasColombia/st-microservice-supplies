package com.ai.st.microservice.supplies.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ai.st.microservice.supplies.entities.SupplyEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;

public interface SupplyRepository extends PagingAndSortingRepository<SupplyEntity, Long> {

    List<SupplyEntity> findByMunicipalityCodeAndStateIn(String municipalityCode, List<SupplyStateEntity> states);

    Page<SupplyEntity> findByMunicipalityCodeAndStateInAndManagerCode(String municipalityCode, List<SupplyStateEntity> states, Long managerCode, Pageable pageable);

    Page<SupplyEntity> findByMunicipalityCodeAndRequestCodeInAndStateIn(String municipalityCode, List<Long> requests, List<SupplyStateEntity> states,
                                                                        Pageable pageable);

    List<SupplyEntity> findByManagerCodeAndModelVersionIsNotNullAndMunicipalityCode(Long managerCode, String municipalityCode);

}
