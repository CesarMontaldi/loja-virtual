package br.com.cesarmontaldi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.cesarmontaldi.ApplicationContextLoad;
import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.service.CorsConfig;

@Component
public class JWTApiAuthFilter extends OncePerRequestFilter {
	
	
	private final TokenService tokenService;
	
	private final UsuarioRepository usuarioRepository;


public JWTApiAuthFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            String login = tokenService.getSubject(tokenJWT);
            Usuario usuario = usuarioRepository.findUserByLogin(login);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            
            CorsConfig.releaseCors(response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
