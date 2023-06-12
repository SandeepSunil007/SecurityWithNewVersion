package com.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
		return httpSecurity.csrf().disable().authorizeHttpRequests().requestMatchers("api/message", "api/addUser")
				.permitAll().and().authorizeHttpRequests().requestMatchers("api/**").authenticated().and().formLogin()
				.and().build();

	}

	// To provide authentication from DB

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(detailsService());
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;
	}
}
