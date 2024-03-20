package com.restaurant.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.enums.UserRole;
import com.restaurant.model.Cart;
import com.restaurant.model.User;
import com.restaurant.repo.CartRepo;
import com.restaurant.repo.UserRepo;
import com.restaurant.request.UserRequest;
import com.restaurant.respponse.AuthRespponse;
import com.restaurant.sucurity.CustomUserDetailService;
import com.restaurant.sucurity.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthController //does not user service or any thing direct interaction with repo....because needs auth
{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider provider;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private CartRepo cartRepo;
	
	//sign up method
	@PostMapping("/signin")
	public ResponseEntity<AuthRespponse> createUserHandler(@RequestBody User user) throws Exception
	{
		User isUserExist= userRepo.findByEmail(user.getEmail());		//not used with user service....because it throws exception if user is empty
		if(isUserExist!=null)
		{
			throw new Exception("Email is already useed with another account");
		}
		else
		{
			//user created
			User createdUser= new User();
			createdUser.setEmail(user.getEmail());
			createdUser.setName(user.getName());
			createdUser.setRole(user.getRole());
			createdUser.setPassword(passwordEncoder.encode(user.getPassword()));	//encoding the password
			
			//user saved
			User saveUser= userRepo.save(createdUser);
			
			//cart created
			Cart cart= new Cart();
			cart.setCustomer(saveUser);//setting the saved user to cart ....not the created user or request body user
			
			//cart saved
			Cart savedCart= cartRepo.save(cart);
			
			//setting the authentication...to generate a security token
			Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			//generating the jwt token
			//to set jwt token we need authentication...ie security token
			String jwt=provider.generateToken(authentication);
			
			AuthRespponse authRespponse = new AuthRespponse();
			authRespponse.setJwt(jwt);
			authRespponse.setMessage("register success");
			authRespponse.setUserRole(saveUser.getRole());
			
			return new ResponseEntity<AuthRespponse>(authRespponse, HttpStatus.CREATED);
		}
		
	}
	
	// sign in using UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())...new user so...only authenticated using direct security token...which validates by saved user...without role
	// log in using UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities())...which goes to jwt token validation filter
	
	@PostMapping("/login")
	public ResponseEntity<AuthRespponse> logIn(@RequestBody UserRequest userRequest)
	{//before authenticating...just check user name and password are correct or not as this saving time..of unnecessary authentication if any one is wrong 
		String username=userRequest.getEmail();
		String password=userRequest.getPassword();
		
		Authentication authentication= authenticate(username,password);		//authenticate method internally calling loadUserByUsername of CustomUserDetailService
		
		//if user name and password matched and got authentication then it is a time to create a Jwt token
		//to set jwt token we need authentication...ie security token...which is generated by calling method authenticate
		String jwt=provider.generateToken(authentication);
		
		AuthRespponse authRespponse = new AuthRespponse();
		authRespponse.setJwt(jwt);
		authRespponse.setMessage("login success");
		
		//for setting the  role
				Collection<? extends GrantedAuthority> authotities = authentication.getAuthorities();
				String role = authotities.isEmpty()?null:authotities.iterator().next().getAuthority();		//authorities is a collection
		
		//Returns the enum constant of the specified enum type with the specified name
		authRespponse.setUserRole(UserRole.valueOf(role));
		
		return new ResponseEntity<AuthRespponse>(authRespponse, HttpStatus.OK);
		
	}

	private Authentication authenticate(String username, String password) 
	{//before authenticating...just check user name and password are correct or not as this saving time..of unnecessary authentication if any one is wrong 
		
		//getting user from user service....loadUserByUsername returns user
		//user service using repo to get the data
		UserDetails userDetails = customUserDetailService.loadUserByUsername(username);		// loadUserByUsername in user details service 
		
		if(userDetails==null)
		{
			throw new BadCredentialsException("Invalid username...");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))		//password encoder has matches method cause..to compare encoded and normal password 
		{
			throw new BadCredentialsException("Invalid password...");
		}
		
		//first argument is email...it automatically takes  or can give email directly
		//security token
		//this line is matched with JwtTokenValidator's UsernamePasswordAuthenticationToken which is in security context....
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
}
