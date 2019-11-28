package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateSupplyOwnerDto", description = "Create Supply Owner Dto")
public class CreateSupplyOwnerDto implements Serializable {

	private static final long serialVersionUID = 8812016046556072388L;

	@ApiModelProperty(required = true, notes = "Owner Type (ENTITY, USER)")
	private String ownerType;

	@ApiModelProperty(required = true, notes = "Owner code")
	private Long ownerCode;

	public CreateSupplyOwnerDto() {

	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Long getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(Long ownerCode) {
		this.ownerCode = ownerCode;
	}

}
