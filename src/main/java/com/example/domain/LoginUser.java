package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final com.example.domain.User user;

	public LoginUser(com.example.domain.User user, Collection<GrantedAuthority> authorityList) {
		super(user.getMailAddress(), user.getPassword(), authorityList);
		this.user = user;
	}

	public com.example.domain.User getUser() {
		return user;
	}
}
