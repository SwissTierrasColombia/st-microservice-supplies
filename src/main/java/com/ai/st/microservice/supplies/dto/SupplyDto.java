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

    @ApiModelProperty(required = true, notes = "State")
    private SupplyStateDto state;

    @ApiModelProperty(required = true, notes = "Municipality Code")
    private String municipalityCode;

    @ApiModelProperty(required = true, notes = "Name")
    private String name;

    @ApiModelProperty(required = true, notes = "Observations")
    private String observations;

    @ApiModelProperty(notes = "Type supply code")
    private Long typeSupplyCode;

    @ApiModelProperty(notes = "Request code")
    private Long requestCode;

    @ApiModelProperty(required = true, notes = "Manager code")
    private Long managerCode;

    @ApiModelProperty(notes = "Model version")
    private String modelVersion;

    @ApiModelProperty(required = true, notes = "Owners")
    private List<SupplyOwnerDto> owners;

    @ApiModelProperty(required = true, notes = "Attachments")
    private List<SupplyAttachmentDto> attachments;

    @ApiModelProperty(notes = "xtf is valid?")
    private Boolean isValid;

    public SupplyDto() {
        this.owners = new ArrayList<>();
        this.attachments = new ArrayList<>();
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

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return "SupplyDto{" + "id=" + id + ", createdAt=" + createdAt + ", state=" + state + ", municipalityCode='"
                + municipalityCode + '\'' + ", name='" + name + '\'' + ", observations='" + observations + '\''
                + ", typeSupplyCode=" + typeSupplyCode + ", requestCode=" + requestCode + ", managerCode=" + managerCode
                + ", modelVersion='" + modelVersion + '\'' + ", owners=" + owners + ", attachments=" + attachments
                + ", isValid=" + isValid + '}';
    }
}
