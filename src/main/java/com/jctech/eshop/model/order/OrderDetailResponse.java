package com.jctech.eshop.model.order;

import java.util.List;

import com.jctech.eshop.model.response.PageResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDetailResponse extends PageResponse {
	@ApiModelProperty(required = true, value = "")
	private List<OrderDetail> items;
}
