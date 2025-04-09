package br.com.cesarmontaldi.security;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.service.CorsService;

@Component
public class JWTApiAuthFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	
	private final UsuarioRepository usuarioRepository;


public JWTApiAuthFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        try {
        if (token != null) {

        	String login = tokenService.getSubject(token);
        	
            Usuario usuario = usuarioRepository.findUserByLogin(login);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            
            CorsService.releaseCors(response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }
        
        filterChain.doFilter(request, response);
        }
        catch (JWTVerificationException e) {
        	response.getWriter().write("Token JWT invalido ou expirado!");
        }
        catch (Exception e) {
        	response.getWriter().write("Ocorreu um erro no sistema, avise o administrador: \n" + e.getMessage());
        }

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
