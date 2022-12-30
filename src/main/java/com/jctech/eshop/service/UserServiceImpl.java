package com.jctech.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.user.User;
import com.jctech.eshop.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public User getUserByUserId(String userId) {
		return userRepo.findOneByUserId(userId).orElseGet(() -> new User());
	}

	@Override
	public User getUserByUserIdAndPassword(String userId, String password) {
		return userRepo.findOneByUserIdAndPassword(userId, password)
				.orElseGet(null);
	}

	@Override
	public User saveOrUpdateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User addNewUser(User user) {
		User newUser = getUserByUserId(user.getUserId());
		if (newUser.getUserId().equals("new")) {
			return this.saveOrUpdateUser(user);
		} else {
			return null;
		}
	}

	@Override
	public String getLoggedInUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return "nosession";
		} else {
			return auth.getName();
		}
	}

	@Override
	public User getLoggedInUser() {
		String loggedInUserId = this.getLoggedInUserId();
		return this.getUserByUserId(loggedInUserId);
	}

}
