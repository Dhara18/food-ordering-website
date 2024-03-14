package com.restaurant.service;

import com.restaurant.model.User;

public interface UserService 
{
	public User findUserByEmail(String email)throws Exception;
	
	public User findByJwtToken(String jwt) throws Exception;
}
