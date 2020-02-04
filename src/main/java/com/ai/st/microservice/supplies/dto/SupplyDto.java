package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyDto", description = "Supply Dto")
public class SupplyDto implements Serializable {

	private static final long serialVersionUID = 8259586718340595896L;

	@ApiModelProperty(required = true, notes = "Supply ID")
	private Long id;

	@ApiModelProperty(required = true, notes = "Date creation")
	private Date createdAt;

	@ApiModelProperty(required = true, notes = "URL")
	private String url;

	@ApiModelProperty(required = true, notes = "State")
	private SupplyStateDto state;

	@ApiModelProperty(required = true, notes = "Municipality Code")
	private String municipalityCode;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = false, notes = "Type supply code")
	private Long typeSupplyCode;

	@ApiModelProperty(required = false, notes = "Request code")
	private Long requestCode;

	@ApiModelProperty(required = false, notes = "Model version")
	private String modelVersion;

	@ApiModelProperty(required = true, notes = "Owners")
	private List<SupplyOwnerDto> owners;

	@ApiModelProperty(required = true, notes = "Attachments")
	private List<SupplyAttachmentDto> attachments;

	public SupplyDto() {
		this.owners = new ArrayList<SupplyOwnerDto>();
		this.attachments = new ArrayList<SupplyAttachmentDto>();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SupplyStateDto getState() {
		return state;
	}

	public void setState(SupplyStateDto state) {
		this.state = state;
	}

	public String getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Long getTypeSupplyCode() {
		return typeSupplyCode;
	}

	public void setTypeSupplyCode(Long typeSupplyCode) {
		this.typeSupplyCode = typeSupplyCode;
	}

	public List<SupplyOwnerDto> getOwners() {
		return owners;
	}

	public void setOwners(List<SupplyOwnerDto> owners) {
		this.owners = owners;
	}

	public List<SupplyAttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SupplyAttachmentDto> attachments) {
		this.attachments = attachments;
	}

	public Long getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(Long requestCode) {
		this.requestCode = requestCode;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(String modelVersion) {
		this.modelVersion = modelVersion;
	}

}
