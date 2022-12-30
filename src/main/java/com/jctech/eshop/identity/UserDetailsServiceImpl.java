package com.jctech.eshop.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jctech.eshop.model.user.User;
import com.jctech.eshop.service.UserService;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserService userService;
	private final AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();
	
	@Override
	public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException {
		final User u = userService.getUserByUserId(username);
		
		TokenUser currentUser;
		 
		if(u.isActive()) {
			currentUser = new TokenUser(u);
		} else{
            throw new DisabledException("User is not activated (Disabled User)");
            //If pending activation return a disabled user
            //currentUser = new TokenUser(user, false);
        }
		
		checker.check(currentUser);
		return currentUser;
	}

}
