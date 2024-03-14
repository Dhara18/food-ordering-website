package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.User;

public interface UserRepo extends JpaRepository<User, Long>
{
	public User findByEmail(String email);
}
