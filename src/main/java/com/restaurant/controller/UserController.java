package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.User;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController 
{
	@Autowired
	private UserService userService;		//get the user from token
	
	@GetMapping("/profile")
	//Authorization=JWT_HEADER
	//if checked with no token inside Auth of postman says forbidden access denied
	//assign request headers Authorization value to jwt
	public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization")String jwt) throws Exception
	{
		User user=userService.findUserByJwtToken(jwt);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
