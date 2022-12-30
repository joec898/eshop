package com.jctech.eshop.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Login {

	@ApiModelProperty(example = "demo", required = true)
	private String username = "";

	@ApiModelProperty(example = "demo", required = true)
	private String password = "";

	public Login(String userName, String password) {
		this.username = userName;
		this.password = password;
	}
}
