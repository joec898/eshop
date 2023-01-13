package com.jctech.eshop.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.response.OperationResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;
import com.jctech.eshop.model.user.User;
import com.jctech.eshop.model.user.UserResponse;
import com.jctech.eshop.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"Authentication"})
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Gets current user information", response = UserResponse.class)
	@GetMapping("/user")
	public UserResponse getUserInfo(@RequestParam(value="userid", required=true) String userId, HttpServletRequest req) {
		UserResponse resp = new UserResponse();
		User user;
		boolean provideUserDetails = false;
		
		String loggedInUserId = userService.getLoggedInUserId();
		
		if(Strings.isNullOrEmpty(userId)) {
			provideUserDetails = true;
			user = userService.getLoggedInUser(); 
		} else if(loggedInUserId.equals(userId)) {
			user = userService.getLoggedInUser();
			provideUserDetails = true;
		} else {
			provideUserDetails = true;
			user = userService.getUserByUserId(userId);
		}
		if(provideUserDetails) {
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		} else {
			resp.setOperationStatus(ResponseStatusEnum.NO_ACCESS);
			resp.setOperationMessage("No Access");
		}
		resp.setData(user);
		return resp;
	}
	
	@ApiOperation(value = "Add new user", response = OperationResponse.class)
	@PostMapping("/user")
	public OperationResponse addNewUser(@RequestBody User user, HttpServletRequest req) {
		User saveUser = userService.addNewUser(user);
		OperationResponse resp = new OperationResponse();
		if (saveUser != null) {
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("User Added");
		} else {
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
			resp.setOperationMessage("Unable to add user");
		}
		return resp;
	}

}
