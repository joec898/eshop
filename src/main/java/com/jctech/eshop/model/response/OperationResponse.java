package com.jctech.eshop.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationResponse {
	public enum ResponseStatusEnum {
		SUCCESS, ERROR, WARNING, NO_ACCESS
	};

	@ApiModelProperty(required = true)
	private ResponseStatusEnum operationStatus;
	private String operationMessage;
}
