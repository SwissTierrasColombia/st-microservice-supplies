package com.ai.st.microservice.supplies.controllers.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.supplies.business.SupplyBusiness;
import com.ai.st.microservice.supplies.dto.CreateSupplyDto;
import com.ai.st.microservice.supplies.dto.CreateSupplyOwnerDto;
import com.ai.st.microservice.supplies.dto.ErrorDto;
import com.ai.st.microservice.supplies.dto.SupplyDto;
import com.ai.st.microservice.supplies.exceptions.BusinessException;
import com.ai.st.microservice.supplies.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Supplies", description = "Manage Supplies", tags = { "Supplies" })
@RestController
@RequestMapping("api/supplies/v1/supplies")
public class SupplyV1Controller {

	private final Logger log = LoggerFactory.getLogger(SupplyV1Controller.class);

	@Autowired
	private SupplyBusiness supplyBusiness;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Supply")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create supply", response = SupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> createSupply(@RequestBody CreateSupplyDto requestCreateSAupply) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation municipality code
			String municipalityCode = requestCreateSAupply.getMunicipalityCode();
			if (municipalityCode.isEmpty()) {
				throw new InputValidationException("El código del municipio es requerido");
			}

			// validation observations
			String observations = requestCreateSAupply.getObservations();
			if (observations.isEmpty()) {
				throw new InputValidationException("Las observaciones son requeridas.");
			}

			// validation owners
			List<CreateSupplyOwnerDto> owners = requestCreateSAupply.getOwners();
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
					requestCreateSAupply.getTypeSupplyCode(), requestCreateSAupply.getRequestCode(),
					requestCreateSAupply.getUrl(), requestCreateSAupply.getUrlsDocumentaryRepository(), owners,
					requestCreateSAupply.getModelVersion());
			httpStatus = HttpStatus.CREATED;

		} catch (InputValidationException e) {
			log.error("Error SupplyV1Controller@createSupply#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error SupplyV1Controller@createSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error SupplyV1Controller@createSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "municipality/{municipalityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get supplies by municipality")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Get supplies", response = SupplyDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getSuppliesByMunicipality(@PathVariable String municipalityId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "requests", required = false) List<Long> requests) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {
			responseDto = supplyBusiness.getSuppliesByMunicipality(municipalityId, page, requests);
			httpStatus = HttpStatus.OK;
		} catch (BusinessException e) {
			log.error("Error SupplyV1Controller@getSuppliesByMunicipality#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error SupplyV1Controller@getSuppliesByMunicipality#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{supplyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get supply by id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Get supply", response = SupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getSupplyById(@PathVariable Long supplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = supplyBusiness.getSupplyById(supplyId);
			httpStatus = (responseDto == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error SupplyV1Controller@getSupplyById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error SupplyV1Controller@getSupplyById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{supplyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete supply by id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Supply deleted", response = SupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> deleteSupplyById(@PathVariable Long supplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			supplyBusiness.deleteSupplyById(supplyId);

			responseDto = new ErrorDto("Se ha eliminado el insumo", 7);
			httpStatus = HttpStatus.NO_CONTENT;

		} catch (BusinessException e) {
			log.error("Error SupplyV1Controller@deleteSupplyById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error SupplyV1Controller@deleteSupplyById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
