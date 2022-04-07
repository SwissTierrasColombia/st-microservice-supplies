package com.ai.st.microservice.supplies.business;

import com.ai.st.microservice.supplies.clients.ManagerFeignClient;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerProfileDto;

import com.ai.st.microservice.supplies.services.tracing.SCMTracing;
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

    public MicroserviceManagerDto getManagerByUserCode(Long userCode) {
        MicroserviceManagerDto managerDto = null;
        try {
            managerDto = managerClient.findByUserCode(userCode);
        } catch (Exception e) {
            String messageError = String.format("Error consultando gestor por el usuario %d : %s", userCode,
                    e.getMessage());
            SCMTracing.sendError(messageError);
            log.error(messageError);
        }
        return managerDto;
    }

}
