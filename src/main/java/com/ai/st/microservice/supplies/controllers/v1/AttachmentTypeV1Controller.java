package com.ai.st.microservice.supplies.controllers.v1;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.supplies.services.tracing.SCMTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.supplies.business.SupplyAttachmentTypeBusiness;
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

    private final SupplyAttachmentTypeBusiness supplyAttachmentTypeBusiness;

    public AttachmentTypeV1Controller(SupplyAttachmentTypeBusiness supplyAttachmentTypeBusiness) {
        this.supplyAttachmentTypeBusiness = supplyAttachmentTypeBusiness;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get attachment's types")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get attachment's types", response = SupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getAttachmentsTypes() {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getAttachmentsTypes");

            responseDto = supplyAttachmentTypeBusiness.getAttachmentsTypes();
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error AttachmentTypeV1Controller@getAttachmentsTypes#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage(), 2);
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error AttachmentTypeV1Controller@getAttachmentsTypes#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage(), 3);
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
