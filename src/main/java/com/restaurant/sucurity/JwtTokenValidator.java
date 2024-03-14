package com.restaurant.sucurity;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//JwtTokenValidator is a filter
//configuring JwtConstant and User using Claims.....so key and header assigned to claims first and...claims already having 	email,and authorities
public class JwtTokenValidator extends OncePerRequestFilter 
{

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)throws ServletException, IOException 
	{
		
		String jwt=request.getHeader(JwtConstant.JWT_HEADER);												//token name
		//Bearer token -Bearer {token name}===>Bearer Authorization..... something like this
		
		if(jwt!=null)
		{
			//because Bearer 6 letters and a space....i.e.....Bearer Authorization(JWT_HEADER or Token)
			//to remove the Bearer keyword
			jwt=jwt.substring(7);
			try
			{
				SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				
				//building claims
				Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				//from the claims extracting email and authorities			//got from User set in the CustomUserServiceDetail class
				//claiming authorities(granted authority)..authority set to User in CustomUserDeatilService
				String email=String.valueOf((claims.get("email")));
				String authorities=String.valueOf((claims.get("authorities")));	//while claiming granted authority converted to String
				
				//authorities=ROLE_CUSTOMER,ROLE_ADMIN,ROLE_OWNER....these are set in class Custom user details...using SimpleGrantedAuthority class
				//here only one... either of the above authorities=ROLE_CUSTOMER
				//while claiming granted authority converted to String... now converted back to list of granted authorities
				List<GrantedAuthority>auth=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				//UserNamePasswordAuthenticationToken
				//token only allow string format not granted authority format...
				Authentication authentication= new UsernamePasswordAuthenticationToken(email,null, auth);
				//setting the token to the security context
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch (Exception e) 
			{
				throw new BadCredentialsException("invalid token");
			}
		}
		
		filterChain.doFilter(request, response);
	}

	
}
