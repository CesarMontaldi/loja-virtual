package br.com.cesarmontaldi.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	
	@Autowired
	private UserDetailsServiceImpl userdetailsService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		.addFilterAfter(new JWTLoginFilter("/login", authenticationManager(), tokenService), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JWTApiAuthFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userdetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity http) throws Exception {
		// http.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso")
		// .antMatchers(HttpMethod.POST, "/salvarAcesso");
	}

}
