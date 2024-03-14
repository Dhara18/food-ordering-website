package com.restaurant.sucurity;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity		//class to have the Spring Security configuration defined in any WebSecurityConfigurer or more likely by exposing a SecurityFilterChain bean
public class SecurityConfig 
{
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(Auth->Auth
				.requestMatchers("/api/admin/**").hasAnyRole("ADMIN","OWNER")		//end points can be accessed if user has any role of ADMIN,OWNER
				.requestMatchers("/api/**").authenticated()							//end point starting with "/api/**" user having any role need to provide JWT token he will be able to access...
				.anyRequest().permitAll()											//all users without token ,having any role can able to access ex..authSignUp,authSignIn for these end points
				)
		.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		.csrf(csrf->csrf.disable())
		.cors(cors->cors.configurationSource(corsConfigurationSource()));
		

		return null;
	}
	//sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).......to make it state less

	private CorsConfigurationSource corsConfigurationSource() 
	{
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) 
			{
				CorsConfiguration cfg= new CorsConfiguration();
				
				cfg.setAllowedOrigins(Arrays.asList(
													"https://dhee-food.vercel.app","http://localhost:8081"
													));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				
				return cfg;
			}
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
