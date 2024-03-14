package com.restaurant.sucurity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//generate Jwt Token
//get email from jwt token
@Service
public class JwtProvider 
{
	private SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	//generating the token of jwtToken
	//taking the UsrnamePasswordAithontication token which is of Authentication type...a token to access user name and password
	public String generateToken(Authentication auth)
	{
		//[auth1,auth2]....auth1====>{"email","password",GTA OBJ having role as string}
		Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();	//one value in collection===>[{"email","password",GTA OBJ having role as string}]
		
		String roles=populateAuthorities(authorities);		//converting to String from collection using poplateAuthorities method 
		
		String jwt= Jwts.builder()	//token building so require string format
						.setIssuedAt(new Date())
						.setExpiration(new Date(new Date().getTime()+86400000))		//exp 24 hrs(86400000)...after that needs to log in
						.claim("email",auth.getName())
						.claim("authorities",roles)		//populateAuthorities...only used here..
						.signWith(key)
						.compact();
		return jwt;
						
	}
	
	//why granted authority is changed from grantedAuthority class to String because....Jwt not allow us to store in ga form but in string form
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) //converting to String from collection using poplateAuthorities method
	{
		Set<String> authSet= new HashSet<>();
		
		for(GrantedAuthority grantedAuthority:authorities)
		{
			authSet.add(grantedAuthority.getAuthority());			//down casting to set from collection to Set
			// after adding one value in set-["email","password","GTA OBJ having role as string"]			------GTA OBJ having role as string=ROLE_CUSTOMER or ROLE_OWNER
		}
		return String.join(",", authSet);	//one value in Set===>[{"email","password",GTA OBJ having role as string}]
		//Concatenates all the elements of a string array, using the specified separator between each element.
		//eg ["	ABC","XYZ"]=====>"ABC,XYZ"
		//eg [{"email","password",GTA OBJ having role as string},{"XYZ"}]=======>[str1,str2]=====>"str1,str2"
	}
	
	//get email from jwt token
	public String getEmailFromJwtrToken(String jwt)//user providing jwt getting email as a return
	{
		jwt=jwt.substring(7);
		
		Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email=String.valueOf((claims.get("email")));
		return email;
	}
}
