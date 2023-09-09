package com.example.citylist.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityAdapter {

	private static final String ANONYMOUS_USER = "anonymous_user";

	public String getAccountUserName() {
		try {
			final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return principal.getUsername();
		} catch (Exception e) {
			return ANONYMOUS_USER;
		}
	}

}
