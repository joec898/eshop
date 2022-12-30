package com.jctech.eshop.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.bind.annotation.RestController;

import com.jctech.eshop.api.session.SessionItem;
import com.jctech.eshop.api.session.SessionResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;
import com.jctech.eshop.model.user.Login;
import com.jctech.eshop.model.user.User;
import com.jctech.eshop.service.UserService;

import io.swagger.annotations.*;


/*
This is a dummy rest controller, for the purpose of documentation (/session) path is map to a filter
 - This will only be invoked if security is disabled
 - If Security is enabled then SessionFilter.java is invoked
 - Enabling and Disabling Security is done at config/applicaton.properties 'security.ignored=/**'
*/

@Api(tags = {"Authentication"})
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SessionController {
	
	@Autowired
	private UserService userService;
	
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Will return a security token, which must be passed in every request", response = SessionResponse.class) })
	@PostMapping("/session")
    @ResponseBody
	public SessionResponse newConnectionSession(@RequestBody Login login,
			HttpServletRequest request, HttpServletResponse response) {
		
		SessionResponse resp = new SessionResponse();
		SessionItem item = new SessionItem();
		
		User user = userService.getUserByUserIdAndPassword(login.getUsername(), login.getPassword());
		
		if(user != null) {
			item.setUserId(user.getUserId());
			item.setFirstName(user.getFirstName());
			item.setLastName(user.getLastName());
			item.setEmail(user.getEmail());  
			item.setToken("xxx.xxx.xxx");
			
			resp.setItem(item);
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("Dummy login sucess");
		} else{
            resp.setOperationStatus(ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Login Failed");
      }
		return resp;
	}
}
