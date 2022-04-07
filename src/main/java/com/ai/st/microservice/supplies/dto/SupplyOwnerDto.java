package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyDto", description = "Supply Dto")
public class SupplyOwnerDto implements Serializable {

    private static final long serialVersionUID = -7648793015124385968L;

    @ApiModelProperty(required = true, notes = "Supply ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Date creation")
    private Date createdAt;

    @ApiModelProperty(required = true, notes = "Owner code")
    private Long ownerCode;

    @ApiModelProperty(required = true, notes = "Owner type")
    private String ownerType;

    public SupplyOwnerDto() {

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

    public Long getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Long ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

}
