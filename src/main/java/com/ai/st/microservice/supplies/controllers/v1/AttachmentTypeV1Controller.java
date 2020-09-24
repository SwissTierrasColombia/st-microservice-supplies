package com.ai.st.microservice.supplies.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.supplies.business.SupplyAttachmentTypeBusiness;
import com.ai.st.microservice.supplies.dto.ErrorDto;
import com.ai.st.microservice.supplies.dto.SupplyDto;
import com.ai.st.microservice.supplies.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Attachment Types", tags = { "Attachment Types" })
@RestController
@RequestMapping("api/supplies/v1/attachments-types")
public class AttachmentTypeV1Controller {
	
	private final Logger log = LoggerFactory.getLogger(AttachmentTypeV1Controller.class);

	@Autowired
	private SupplyAttachmentTypeBusiness supplyAttachmentTypeBusiness;	

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get attachmentes types")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Get attachmentes types", response = SupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getAttachmentsTypes() {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = supplyAttachmentTypeBusiness.getAttachmentsTypes();
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error AttachmentTypeV1Controller@getAttachmentsTypes#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error AttachmentTypeV1Controller@getAttachmentsTypes#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
