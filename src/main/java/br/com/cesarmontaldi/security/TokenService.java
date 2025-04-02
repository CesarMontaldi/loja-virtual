package br.com.cesarmontaldi.security;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	private String secret = "123456";



    public String gerarToken(String username) throws IOException {

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API Loja-Virtual")
                    .withSubject(username)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
    		
    		return token;
    		
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) throws IOException {
    	
      var algoritimo = Algorithm.HMAC256(secret);
      return JWT.require(algoritimo)
             .withIssuer("API Loja-Virtual")
             .build()
             .verify(tokenJWT)
             .getSubject();

//       try {
//            var algoritimo = Algorithm.HMAC256(secret);
//             return JWT.require(algoritimo)
//                    .withIssuer("API Loja-Virtual")
//                    .build()
//                    .verify(tokenJWT)
//                    .getSubject();
//       	} catch (JWTVerificationException exception) {
//        	throw new RuntimeException("Token JWT inválido ou expirado!", exception);
//        }

    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }

}
