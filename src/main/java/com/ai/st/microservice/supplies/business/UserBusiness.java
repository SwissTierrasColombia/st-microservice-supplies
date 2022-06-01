package com.ai.st.microservice.supplies.business;

import com.ai.st.microservice.supplies.clients.UserFeignClient;
import com.ai.st.microservice.supplies.dto.administration.MicroserviceRoleDto;
import com.ai.st.microservice.supplies.dto.administration.MicroserviceUserDto;
import com.ai.st.microservice.supplies.services.tracing.SCMTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusiness {

    private final Logger log = LoggerFactory.getLogger(UserBusiness.class);

    @Autowired
    private UserFeignClient userClient;

    public MicroserviceUserDto getUserByToken(String headerAuthorization) {
        MicroserviceUserDto userDto = null;
        try {
            String token = headerAuthorization.replace("Bearer ", "").trim();
            userDto = userClient.findByToken(token);
        } catch (Exception e) {
            String messageError = String.format("Error consultando el usuario a partir del token : %s", e.getMessage());
            SCMTracing.sendError(messageError);
            log.error(messageError);
        }
        return userDto;
    }

}
