package com.ai.st.microservice.supplies.controllers.v2;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.supplies.business.ManagerBusiness;
import com.ai.st.microservice.supplies.business.SupplyBusiness;
import com.ai.st.microservice.supplies.business.UserBusiness;
import com.ai.st.microservice.supplies.dto.*;
import com.ai.st.microservice.supplies.dto.administration.MicroserviceUserDto;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.supplies.exceptions.FeignMicroserviceException;
import com.ai.st.microservice.supplies.services.tracing.SCMTracing;
import com.ai.st.microservice.supplies.services.tracing.TracingKeyword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Manage Supplies", tags = { "Supplies" })
@RestController
@RequestMapping("api/supplies/v2/supplies")
public class SupplyV2Controller {

    private final Logger log = LoggerFactory.getLogger(SupplyV2Controller.class);

    private final SupplyBusiness supplyBusiness;
    private final UserBusiness userBusiness;
    private final ManagerBusiness managerBusiness;

    public SupplyV2Controller(SupplyBusiness supplyBusiness, UserBusiness userBusiness,
            ManagerBusiness managerBusiness) {
        this.supplyBusiness = supplyBusiness;
        this.userBusiness = userBusiness;
        this.managerBusiness = managerBusiness;
    }

    @GetMapping(value = "/xtf/{municipalityCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supplies by municipality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get supplies", response = SupplyDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getSuppliesXTFByMunicipality(@PathVariable String municipalityCode,
            @RequestHeader("authorization") String headerAuthorization) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getSuppliesXTFByMunicipality");
            SCMTracing.addCustomParameter(TracingKeyword.AUTHORIZATION_HEADER, headerAuthorization);

            MicroserviceUserDto userDtoSession = userBusiness.getUserByToken(headerAuthorization);
            if (userDtoSession == null) {
                throw new FeignMicroserviceException("Ha ocurrido un error consultando el usuario");
            }
            SCMTracing.addCustomParameter(TracingKeyword.USER_ID, userDtoSession.getId());
            SCMTracing.addCustomParameter(TracingKeyword.USER_EMAIL, userDtoSession.getEmail());
            SCMTracing.addCustomParameter(TracingKeyword.USER_NAME, userDtoSession.getUsername());

            MicroserviceManagerDto managerDto = managerBusiness.getManagerByUserCode(userDtoSession.getId());
            if (managerDto == null) {
                throw new FeignMicroserviceException("Ha ocurrido un error consultando el gestor.");
            }
            SCMTracing.addCustomParameter(TracingKeyword.MANAGER_ID, managerDto.getId());
            SCMTracing.addCustomParameter(TracingKeyword.MANAGER_NAME, managerDto.getName());

            responseDto = supplyBusiness.getSuppliesXTFByMunicipality(municipalityCode, managerDto.getId());
            httpStatus = HttpStatus.OK;

        } catch (FeignMicroserviceException e) {
            log.error("Error SupplyV2Controller@getSuppliesXTFByMunicipality#Microservice ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV2Controller@getSuppliesXTFByMunicipality#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
