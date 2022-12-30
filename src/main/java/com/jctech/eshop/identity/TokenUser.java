package com.jctech.eshop.identity;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jctech.eshop.model.user.User;

public class TokenUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private User user;

	public TokenUser(User user) {
		super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}
	
    public User getUser() {
        return user;
    }

    public String getRole() {
        return user.getRole().toString();
    } 
	
    @Override
    public List<GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return authorities;
    }
    
    // temp implementation to support un-encrypted password in db
    // normally only encrypted passwords should be in db
	@Override
	public String getPassword() { 
		// this is to support 
		// org.springframework.security.crypto.password.PasswordEncoder matches (CharSequence rawPassword, String encodedPassword) 
		String p = this.getUser().getPassword();
		p = new BCryptPasswordEncoder().encode(p);
		return p;
		//return this.getPassword();
	}
}
