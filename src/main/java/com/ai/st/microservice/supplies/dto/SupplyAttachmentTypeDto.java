package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SupplyAttachmentTypeDto")
public class SupplyAttachmentTypeDto implements Serializable {

    private static final long serialVersionUID = 5501241816417835256L;

    @ApiModelProperty(required = true, notes = "Attachment Type ID")
    private Long id;

    @ApiModelProperty(required = true, notes = "Name")
    private String name;

    public SupplyAttachmentTypeDto() {

    }

    public SupplyAttachmentTypeDto(Long id, String name) {
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
