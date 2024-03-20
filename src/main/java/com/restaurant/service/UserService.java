package com.restaurant.service;

import com.restaurant.model.User;

public interface UserService 
{
	public User findUserByEmail(String email)throws Exception;
	
	public User findUserByJwtToken(String jwt) throws Exception;
}
