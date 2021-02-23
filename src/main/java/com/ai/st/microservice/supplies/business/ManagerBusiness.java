package com.ai.st.microservice.supplies.business;

import com.ai.st.microservice.supplies.clients.ManagerFeignClient;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerProfileDto;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagerBusiness {

    private final Logger log = LoggerFactory.getLogger(ManagerBusiness.class);

    @Autowired
    private ManagerFeignClient managerClient;

    public MicroserviceManagerDto getManagerById(Long managerId) {
        MicroserviceManagerDto managerDto = null;
        try {
            managerDto = managerClient.findById(managerId);
        } catch (Exception e) {
            log.error("No se ha podido consultar el gestor: " + e.getMessage());
        }
        return managerDto;
    }

    public MicroserviceManagerDto getManagerByUserCode(Long userCode) {
        MicroserviceManagerDto managerDto = null;
        try {
            managerDto = managerClient.findByUserCode(userCode);
        } catch (Exception e) {
            log.error("No se ha podido consultar el gestor: " + e.getMessage());
        }
        return managerDto;
    }


    public boolean userManagerIsDirector(Long userCode) {

        Boolean isDirector = false;

        try {

            List<MicroserviceManagerProfileDto> managerProfiles = managerClient.findProfilesByUser(userCode);

            MicroserviceManagerProfileDto profileDirector = managerProfiles.stream()
                    .filter(profileDto -> profileDto.getId().equals(RoleBusiness.SUB_ROLE_DIRECTOR)).findAny()
                    .orElse(null);

            if (profileDirector instanceof MicroserviceManagerProfileDto) {
                isDirector = true;
            }

        } catch (FeignException e) {
            log.error("No se ha podido verificar si el usuario es un director(gestor): " + e.getMessage());
        }

        return isDirector;
    }

}
