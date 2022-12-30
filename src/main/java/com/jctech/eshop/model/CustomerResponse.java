package com.jctech.eshop.model;


import lombok.*;

import java.util.*;
import com.jctech.eshop.model.response.*;

import io.swagger.annotations.ApiModelProperty;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerResponse extends PageResponse {
  @ApiModelProperty(required = true, value = "")
  private List<Customer> items;
}
