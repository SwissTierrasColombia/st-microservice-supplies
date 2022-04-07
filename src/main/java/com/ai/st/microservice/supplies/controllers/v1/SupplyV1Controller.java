package com.ai.st.microservice.supplies.controllers.v1;

import java.util.List;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.supplies.services.tracing.SCMTracing;
import com.ai.st.microservice.supplies.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.supplies.business.SupplyBusiness;
import com.ai.st.microservice.supplies.dto.CreateSupplyDto;
import com.ai.st.microservice.supplies.dto.CreateSupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.SupplyDto;
import com.ai.st.microservice.supplies.dto.UpdateSupplyDto;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Supplies", tags = { "Supplies" })
@RestController
@RequestMapping("api/supplies/v1/supplies")
public class SupplyV1Controller {

    private final Logger log = LoggerFactory.getLogger(SupplyV1Controller.class);

    private final SupplyBusiness supplyBusiness;

    public SupplyV1Controller(SupplyBusiness supplyBusiness) {
        this.supplyBusiness = supplyBusiness;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create Supply")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Supply created", response = SupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createSupply(@RequestBody CreateSupplyDto requestCreateSupply) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createSupply");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, requestCreateSupply.toString());

            // validation municipality code
            String municipalityCode = requestCreateSupply.getMunicipalityCode();
            if (municipalityCode.isEmpty()) {
                throw new InputValidationException("El código del municipio es requerido");
            }

            // validation manager code
            Long managerCode = requestCreateSupply.getManagerCode();
            if (managerCode == null || managerCode <= 0) {
                throw new InputValidationException("El código del gestor es requerido");
            }

            // validation observations
            String observations = requestCreateSupply.getObservations();
            if (observations.isEmpty()) {
                throw new InputValidationException("Las observaciones son requeridas.");
            }

            // validation owners
            List<CreateSupplyOwnerDto> owners = requestCreateSupply.getOwners();
            if (owners.size() > 0) {
                for (CreateSupplyOwnerDto owner : owners) {
                    if (owner.getOwnerCode() == null || owner.getOwnerCode() <= 0) {
                        throw new InputValidationException("El código de propietario es inválido.");
                    }
                    if (owner.getOwnerType() == null || owner.getOwnerType().isEmpty()) {
                        throw new InputValidationException("El typo de propietario es inválido.");
                    }
                }
            } else {
                throw new InputValidationException("El insumo debe tener al menos un propietario.");
            }

            responseDto = supplyBusiness.addSupplyToMunicipality(municipalityCode, observations,
                    requestCreateSupply.getTypeSupplyCode(), managerCode, requestCreateSupply.getRequestCode(),
                    requestCreateSupply.getAttachments(), owners, requestCreateSupply.getModelVersion(),
                    requestCreateSupply.getSupplyStateId(), requestCreateSupply.getName(),
                    requestCreateSupply.getValid());
            httpStatus = HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error SupplyV1Controller@createSupply#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error SupplyV1Controller@createSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV1Controller@createSupply#General ---> " + e.getMessage());
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "municipality/{municipalityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supplies by municipality")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Get supplies", response = SupplyDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getSuppliesByMunicipality(@PathVariable String municipalityId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "manager", required = false) Long managerCode,
            @RequestParam(name = "requests", required = false) List<Long> requests,
            @RequestParam(name = "states", required = false) List<Long> states) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getSuppliesByMunicipality");

            responseDto = supplyBusiness.getSuppliesByMunicipality(municipalityId, page, requests, states, managerCode);
            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            log.error("Error SupplyV1Controller@getSuppliesByMunicipality#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV1Controller@getSuppliesByMunicipality#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "{supplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supply by id")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Get supply", response = SupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getSupplyById(@PathVariable Long supplyId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getSupplyById");

            responseDto = supplyBusiness.getSupplyById(supplyId);
            httpStatus = (responseDto == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error SupplyV1Controller@getSupplyById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV1Controller@getSupplyById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @DeleteMapping(value = "{supplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete supply by id")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Supply deleted", response = SupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> deleteSupplyById(@PathVariable Long supplyId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("deleteSupplyById");

            supplyBusiness.deleteSupplyById(supplyId);

            responseDto = new BasicResponseDto("Se ha eliminado el insumo");
            httpStatus = HttpStatus.NO_CONTENT;

        } catch (BusinessException e) {
            log.error("Error SupplyV1Controller@deleteSupplyById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV1Controller@deleteSupplyById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "{supplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update supply by id")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Supply updated", response = SupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateSupply(@PathVariable Long supplyId, @RequestBody UpdateSupplyDto updateSupplyDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateSupply");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, updateSupplyDto.toString());

            responseDto = supplyBusiness.updateSupply(supplyId, updateSupplyDto.getStateId());
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error SupplyV1Controller@updateSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyV1Controller@updateSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
