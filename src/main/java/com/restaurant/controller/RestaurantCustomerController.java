package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantCustomerController 
{
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>>searchRestaurant(@RequestHeader("Authorization")String jwt,@RequestParam String keyword) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		
		List<Restaurant> restaurantList = restaurantService.searchRestaurants(keyword);
		
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.FOUND);
	}
	
	@GetMapping()				//when restaurant end point hits it should get all the restaurants
	public ResponseEntity<List<Restaurant>>getAllRestaurant(@RequestHeader("Authorization")String jwt) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		
		List<Restaurant> restaurantList = restaurantService.getAllRestaurants();
		
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant>findRestaurantById(@RequestHeader("Authorization")String jwt,@PathVariable Long id) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		
		Restaurant restaurant = restaurantService.findRestaurantById(id);
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.FOUND);
	}
	
	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDTO>addToFavorate(@RequestHeader("Authorization")String jwt,@PathVariable Long id) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		
		RestaurantDTO restaurantDTO = restaurantService.addToFavorates(id,user);
		
		return new ResponseEntity<RestaurantDTO>(restaurantDTO, HttpStatus.CREATED);
	}
}
