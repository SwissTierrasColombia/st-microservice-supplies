package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyAttachmentDto")
public class SupplyAttachmentDto implements Serializable {

	private static final long serialVersionUID = 7112301654715963689L;

	@ApiModelProperty(required = true, notes = "Attachment ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "URL")
	private String data;

	@ApiModelProperty(required = true, notes = "Type")
	private SupplyAttachmentTypeDto attachmentType;

	public SupplyAttachmentDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SupplyAttachmentTypeDto getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(SupplyAttachmentTypeDto attachmentType) {
		this.attachmentType = attachmentType;
	}

}
