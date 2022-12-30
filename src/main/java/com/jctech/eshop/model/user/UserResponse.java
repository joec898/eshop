package com.jctech.eshop.model.user;
 
import com.jctech.eshop.model.response.OperationResponse;
 
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends OperationResponse {
	private User data = new User();
}