package com.jctech.eshop.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.jctech.eshop.model.response.OperationResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;

@ControllerAdvice  // tells your spring application that this class will do the exception handling for your application.
@RestController    // makes it a controller and let this class render the response.
public class GlobalExceptionHandler {
	// @ExceptionHandler annotation to define the class of Exception it will catch. (A 
	//   Base class will catch all the Inherited and extended classes)
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public OperationResponse handleBaseException(DataIntegrityViolationException e) {
		OperationResponse resp = new OperationResponse();
        resp.setOperationStatus(ResponseStatusEnum.ERROR);
        resp.setOperationMessage(e.getRootCause().getMessage());
        return resp;
	}
}
