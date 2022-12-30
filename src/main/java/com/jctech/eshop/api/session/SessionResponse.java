package com.jctech.eshop.api.session;

import com.jctech.eshop.model.response.OperationResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionResponse extends OperationResponse{
	@ApiModelProperty(required = true, value = "")
	private SessionItem item;
}
