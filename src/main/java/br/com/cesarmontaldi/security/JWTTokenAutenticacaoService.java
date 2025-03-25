package br.com.cesarmontaldi.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {
	
	private static final long EXPIRATION_TIME = 86400000;
	
	@Value("${application.secret.jwt}")
	private static String SECRET_JWT;
	
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";
	
	
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		String jwt = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_JWT)
				.compact();
		
		String token = TOKEN_PREFIX + " "  + jwt;
		
		response.addHeader(HEADER_STRING, token);
		
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}
}
