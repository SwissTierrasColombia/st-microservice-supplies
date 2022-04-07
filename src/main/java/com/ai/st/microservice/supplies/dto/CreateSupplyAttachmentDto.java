package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CreateSupplyAttachmentDto")
public class CreateSupplyAttachmentDto implements Serializable {

    private static final long serialVersionUID = 335203584412319641L;

    @ApiModelProperty(notes = "Data")
    private String data;

    @ApiModelProperty(notes = "Attachment Type ID")
    private Long attachmentTypeId;

    public CreateSupplyAttachmentDto() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Long attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    @Override
    public String toString() {
        return "CreateSupplyAttachmentDto{" + "data='" + data + '\'' + ", attachmentTypeId=" + attachmentTypeId + '}';
    }
}
