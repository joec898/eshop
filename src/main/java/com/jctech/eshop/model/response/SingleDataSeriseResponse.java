package com.jctech.eshop.model.response;

import java.util.List;

import com.jctech.eshop.model.SingleSerise;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SingleDataSeriseResponse extends OperationResponse {
	private List<SingleSerise> items;
}
