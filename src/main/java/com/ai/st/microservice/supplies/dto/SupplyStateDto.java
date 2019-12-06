package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyStateDto", description = "Supply State Dto")
public class SupplyStateDto implements Serializable {

	private static final long serialVersionUID = 5317228706593217348L;

	@ApiModelProperty(required = true, notes = "State ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "State name")
	private String name;

	public SupplyStateDto() {

	}

	public SupplyStateDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
