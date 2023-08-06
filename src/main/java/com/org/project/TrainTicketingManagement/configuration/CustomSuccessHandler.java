package com.org.project.TrainTicketingManagement.configuration;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		if(role.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/");
		}else if(role.contains("ROLE_DRIVAER")) {
			response.sendRedirect("/driver/");
		}else {
			response.sendRedirect("/client/");
		}
		
	}

}
