package com.ai.st.microservice.supplies.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UpdateSupplyDto")
public class UpdateSupplyDto implements Serializable {

    private static final long serialVersionUID = -1832068905400656799L;

    @ApiModelProperty(required = true, notes = "State ID")
    private Long stateId;

    public UpdateSupplyDto() {

    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

}
