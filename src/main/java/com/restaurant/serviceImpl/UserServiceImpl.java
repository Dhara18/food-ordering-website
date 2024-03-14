package com.restaurant.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.model.User;
import com.restaurant.repo.UserRepo;
import com.restaurant.service.UserService;
import com.restaurant.sucurity.JwtProvider;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByEmail(String email) throws Exception 
	{
		User user = userRepo.findByEmail(email);
		if(user==null)
		{
			throw new UsernameNotFoundException("user not found");
		}
		return user;
	}
	
	@Override
	public User findByJwtToken(String jwt) throws Exception 
	{
		String email = jwtProvider.getEmailFromJwtrToken(jwt);
		
		User user = userRepo.findByEmail(email);
		
		return user;
	}


}
