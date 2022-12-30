package com.jctech.eshop.service;

import com.jctech.eshop.model.user.User;

public interface UserService { 
	User getUserByUserId(String userId);
	User getUserByUserIdAndPassword(String userId, String password);
	User saveOrUpdateUser(User user);
	User addNewUser(User user);
	String getLoggedInUserId();
	User getLoggedInUser();
}
