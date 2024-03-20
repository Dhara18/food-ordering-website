package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.RestaureantRequest;

public interface RestaurantService 
{
	public Restaurant createRestaurant(RestaureantRequest req,User user);
	
	public Restaurant findRestaurantById(Long restaurantId)throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId)throws Exception;
	
	//only Admin can access this method
	public List<Restaurant>getAllRestaurants();
		
	public List<Restaurant>searchRestaurants(String keyword);
	
	public Restaurant updateRestaurant(long restaurantId,RestaureantRequest req)throws Exception; 
	
	public void deleteRestaurant(long restaurantId)throws Exception;
	
	public RestaurantDTO addToFavorates(Long restaurantId,User user)throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id)throws Exception;
}
