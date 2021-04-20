package com.ai.st.microservice.supplies.controllers.v2;

import com.ai.st.microservice.supplies.business.ManagerBusiness;
import com.ai.st.microservice.supplies.business.SupplyBusiness;
import com.ai.st.microservice.supplies.business.UserBusiness;
import com.ai.st.microservice.supplies.dto.*;
import com.ai.st.microservice.supplies.dto.administration.MicroserviceUserDto;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.exceptions.FeignMicroserviceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Manage Supplies", tags = {"Supplies"})
@RestController
@RequestMapping("api/supplies/v2/supplies")
public class SupplyV2Controller {

    private final Logger log = LoggerFactory.getLogger(SupplyV2Controller.class);

    @Autowired
    private SupplyBusiness supplyBusiness;

    @Autowired
    private UserBusiness userBusiness;

    @Autowired
    private ManagerBusiness managerBusiness;

    @RequestMapping(value = "/xtf/{municipalityCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supplies by municipality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get supplies", response = SupplyDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class)})
    @ResponseBody
    public ResponseEntity<?> getSuppliesXTFByMunicipality(@PathVariable String municipalityCode,
                                                          @RequestHeader("authorization") String headerAuthorization) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            // user session
            MicroserviceUserDto userDtoSession = userBusiness.getUserByToken(headerAuthorization);
            if (userDtoSession == null) {
                throw new FeignMicroserviceException("Ha ocurrido un error consultando el usuario");
            }

            // get manager
            MicroserviceManagerDto managerDto = managerBusiness.getManagerByUserCode(userDtoSession.getId());
            if (managerDto == null) {
                throw new FeignMicroserviceException("Ha ocurrido un error consultando el gestor.");
            }

            responseDto = supplyBusiness.getSuppliesXTFByMunicipality(municipalityCode, managerDto.getId());
            httpStatus = HttpStatus.OK;

        } catch (FeignMicroserviceException e) {
            log.error("Error SupplyV2Controller@getSuppliesXTFByMunicipality#Microservice ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error SupplyV2Controller@getSuppliesXTFByMunicipality#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
