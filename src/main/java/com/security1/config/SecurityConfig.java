package com.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security1.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // for Method level authorization
public class SecurityConfig {

	// 1. HardCoded Code

//	@Bean
//	// Authentication
//	public UserDetailsService detailsService(PasswordEncoder passwordEncoder) {
//		UserDetails admin =  User.withUsername("Sandeep")
//				.password(passwordEncoder.encode("sandy"))
//				.roles("ADMIN").build();
//		UserDetails user =  User.withUsername("Sunil")
//				.password(passwordEncoder.encode("sunny"))
//				.roles("USER").build();
//		return new InMemoryUserDetailsManager(admin, user);	
//	}

	
	// JWT filter the Token
	
		@Autowired
		private JwtFilter filter;
	
	
	@Bean
	// Authentication
	public UserDetailsService detailsService() {
		return new UserInfoUserDetails();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	// Authorization
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("api/message", "api/addUser","api/authenticate")
				.permitAll().and().authorizeHttpRequests()
				.requestMatchers("api/**").authenticated()
				.and()
//				.formLogin().and().build();
				.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

	}

	// To provide authentication from DB

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(detailsService());
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;
	}
	
	// Jwt Authentication from DB
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	
	
	
}
