package com.adi685.blogging_web_app.config;

import com.adi685.blogging_web_app.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailService customUserDetailService;

	private final PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/").permitAll() // Allow unauthenticated access to /public/**
						.anyRequest().authenticated() // Require authentication for any other request
				)
				.httpBasic(withDefaults()); // Use HTTP Basic authentication
		httpSecurity.authenticationProvider(daoAuthenticationProvider());
		return httpSecurity.build();


	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


}
