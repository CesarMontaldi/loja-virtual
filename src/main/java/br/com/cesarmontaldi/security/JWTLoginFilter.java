package br.com.cesarmontaldi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.service.CorsConfig;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private final TokenService tokenService;

	
	public JWTLoginFilter(String url, AuthenticationManager authManager, TokenService tokenService) {
		super(new AntPathRequestMatcher(url));
		this.tokenService = tokenService;
		setAuthenticationManager(authManager);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		try {
			String token = tokenService.gerarToken(authentication.getName());
			CorsConfig.releaseCors(response);
			response.addHeader("Authorization", token);
    		response.getWriter().write(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class); 

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}

}
