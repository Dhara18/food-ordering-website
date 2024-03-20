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
@EnableWebSecurity		//to tell the spring security context that there exist a customized SecurityFilterChain	
//class to have the Spring Security configuration defined in any WebSecurityConfigurer or more likely by exposing a SecurityFilterChain bean
public class SecurityConfig 
{
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(Auth->Auth
				.requestMatchers("/api/admin/**").hasAnyRole("ADMIN","OWNER")		//end points can be accessed if user has any role of ADMIN,OWNER
				//authenticated using token so...jwt token required to access
				.requestMatchers("/api/**").authenticated()							//end point starting with "/api/**" user having any role need to provide JWT token he will be able to access...
				.anyRequest().permitAll()											//all users without token ,having any role can able to access ex..authSignUp,authSignIn for these end points
				)
		.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)	//add JwtTokenValidator before BasicAuthonticationFilter
		.csrf(csrf->csrf.disable())
		.cors(cors->cors.configurationSource(corsConfigurationSource()));
		

		return http.build();
	}
	//sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).......to make it state less

	private CorsConfigurationSource corsConfigurationSource() 
	{
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) //request was not used anywhere why?
			{
				CorsConfiguration cfg= new CorsConfiguration();
				
				cfg.setAllowedOrigins(Arrays.asList(								//front end URLS from which back end can be accessible
													"https://dhee-food.vercel.app",
													"http://localhost:8081"			//if not deployed this URL is needed to access the api
													));
				cfg.setAllowedMethods(Collections.singletonList("*"));				//methods allowed for front end URL...* means all
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));				//unmodifiable List containing one object....neither can add nor remove
				cfg.setExposedHeaders(Arrays.asList("Authorization"));				//can give singleton..but we only got one header...i.e.  JwtConstant.JWT_HEADER="Authorization"
				cfg.setMaxAge(3600L);			//time web site linked with cors
				
				return cfg;
			}
			//unmodifiable List containing one object
			//The singletonList() method of java.util.Collections class is used to return an immutable list containing only the specified object. 
			//The returned list is serializable. This list will always contain only one element thus the name singleton list. 
			//When we try to add/remove an element on the returned singleton list, it would give UnsupportedOperationException.
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();											//beCrypted password get stored in the database
	}
}
