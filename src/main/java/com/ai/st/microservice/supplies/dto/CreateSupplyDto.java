package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateSupplyDto")
public class CreateSupplyDto implements Serializable {

    private static final long serialVersionUID = 7367459622173301312L;

    @ApiModelProperty(required = true, notes = "Municipality Code (DIVIPOLA)")
    private String municipalityCode;

    @ApiModelProperty(required = true, notes = "Name")
    private String name;

    @ApiModelProperty(required = true, notes = "Observations")
    private String observations;

    @ApiModelProperty(notes = "Type Supply Code")
    private Long typeSupplyCode;

    @ApiModelProperty(notes = "Type Supply Code")
    private Long requestCode;

    @ApiModelProperty(required = true, notes = "Manager Code")
    private Long managerCode;

    @ApiModelProperty(notes = "Model version")
    private String modelVersion;

    @ApiModelProperty(notes = "State ID")
    private Long supplyStateId;

    @ApiModelProperty(required = true, notes = "Owners")
    private List<CreateSupplyOwnerDto> owners;

    @ApiModelProperty(notes = "Attachments")
    private List<CreateSupplyAttachmentDto> attachments;

    @ApiModelProperty(notes = "Has geometry validation?")
    private Boolean hasGeometryValidation;

    public CreateSupplyDto() {
        this.owners = new ArrayList<>();
        this.attachments = new ArrayList<>();
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

    public List<CreateSupplyAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CreateSupplyAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public Long getSupplyStateId() {
        return supplyStateId;
    }

    public void setSupplyStateId(Long supplyStateId) {
        this.supplyStateId = supplyStateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(Long managerCode) {
        this.managerCode = managerCode;
    }

    public Boolean getHasGeometryValidation() {
        return hasGeometryValidation;
    }

    public void setHasGeometryValidation(Boolean hasGeometryValidation) {
        this.hasGeometryValidation = hasGeometryValidation;
    }
}
