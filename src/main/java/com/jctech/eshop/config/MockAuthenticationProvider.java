package com.jctech.eshop.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.jctech.eshop.model.user.ERole;
import com.jctech.eshop.model.user.Role;
import com.jctech.eshop.model.user.User;

/**
 * A authentication provider simulating accessing google api to verify an user
 */
@Component
public class MockAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());

		// We fetch user from somewhere like Google API 
		User user = getUserFromMockCloud(username, password);
		if (user != null) {
			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					password, authorities);

			return authenticationToken;
		}
		throw new BadCredentialsException("Error!!");
	}

	// Let's assume the Mock API will return the user in this method.
	private User getUserFromMockCloud(String username, String password) {
		Map<String, String> users = new HashMap<>();

		users.put("tester2", "123456789");
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(ERole.USER));

		if (users.get(username) != null) {
			User u = new User(username, password, "","");
			u.setRoles(roles);
			return u;
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authenticationType) {
		return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
	}

}
