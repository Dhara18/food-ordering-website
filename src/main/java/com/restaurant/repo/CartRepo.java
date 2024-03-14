package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>
{

}
