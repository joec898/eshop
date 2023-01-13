package com.jctech.eshop.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jctech.eshop.identity.TokenUtil;
import com.jctech.eshop.identity.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.core.annotation.Order;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private UnauthorizedEntryPoint unauthorizedHandler;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
    MockAuthenticationProvider mockCloudAuthenticationProvider;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//		return authConfig.getAuthenticationManager();
//	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/", "/resources/**", "/static/**", "/public/**", "/webui/**",
				"/h2-console/**", "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs",
				"/api-docs/**", "/v2/api-docs/**", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png",
				"/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.otf");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and().anonymous()
				.and()
		        .sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
				.addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new GenerateTokenForUserFilter("/session", authenticationManager(), tokenUtil), UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider()).authenticationProvider(mockCloudAuthenticationProvider) 
				.authorizeRequests()
				.antMatchers("/user/**").hasAnyAuthority("ADMIN", "MODERATOR")
				.antMatchers("/session").permitAll()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated() ;

		return http.build();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().and().anonymous().and()
				// Disable Cross site references
				.csrf().disable()
				// Add CORS Filter
				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
				// Custom Token based authentication based on the header previously given to the
				// client
				.addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
				// custom JSON based authentication by POST of
				// {"username":"<name>","password":"<password>"} which sets the token header upon authentication
				 .addFilterBefore(new GenerateTokenForUserFilter ("/session",
				  authenticationManager(), tokenUtil),  UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().anyRequest().authenticated();
	}

}
