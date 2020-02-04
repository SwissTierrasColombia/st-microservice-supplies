package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateSupplyDto", description = "Create Supply Dto")
public class CreateSupplyDto implements Serializable {

	private static final long serialVersionUID = 7367459622173301312L;

	@ApiModelProperty(required = false, notes = "Supply URL")
	private String url;

	@ApiModelProperty(required = true, notes = "Municipality Code (DIVIPOLA)")
	private String municipalityCode;

	@ApiModelProperty(required = true, notes = "Observations")
	private String observations;

	@ApiModelProperty(required = false, notes = "Type Supply Code")
	private Long typeSupplyCode;

	@ApiModelProperty(required = false, notes = "Type Supply Code")
	private Long requestCode;

	@ApiModelProperty(required = false, notes = "Model version")
	private String modelVersion;

	@ApiModelProperty(required = true, notes = "Owners")
	private List<CreateSupplyOwnerDto> owners;

	@ApiModelProperty(required = false, notes = "Urls documentary repository")
	private List<String> urlsDocumentaryRepository;

	public CreateSupplyDto() {
		this.owners = new ArrayList<CreateSupplyOwnerDto>();
		this.urlsDocumentaryRepository = new ArrayList<String>();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public List<CreateSupplyOwnerDto> getOwners() {
		return owners;
	}

	public void setOwners(List<CreateSupplyOwnerDto> owners) {
		this.owners = owners;
	}

	public List<String> getUrlsDocumentaryRepository() {
		return urlsDocumentaryRepository;
	}

	public void setUrlsDocumentaryRepository(List<String> urlsDocumentaryRepository) {
		this.urlsDocumentaryRepository = urlsDocumentaryRepository;
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
