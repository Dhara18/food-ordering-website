package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.request.RestaureantRequest;
import com.restaurant.respponse.MessageResponse;
import com.restaurant.service.RestaurantService;
import com.restaurant.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class RestaurantAdminController 				//admin ie owner can (add,update,update status,delete) restaurant,and owner can find restaurant by his id 
{
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;		//always get the user from token
	
	@PostMapping()
	public ResponseEntity<Restaurant>createRestaurant(@RequestBody RestaureantRequest restaureantRequest,
														@RequestHeader("Authorization")String jwt
														) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.createRestaurant(restaureantRequest, user);
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Restaurant>upDateRestaurant(@RequestBody RestaureantRequest restaureantRequest,
														@RequestHeader("Authorization")String jwt,
														@PathVariable long id
														) throws Exception
	{
		//not necessary...as owner ie user is already set and not setting the user in the update method
		User user = userService.findUserByJwtToken(jwt);
		
		Restaurant restaurant = restaurantService.updateRestaurant(id,restaureantRequest);
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant>upDateRestaurantStatus(@RequestHeader("Authorization")String jwt,
														@PathVariable long id
														) throws Exception
	{
		//not necessary...as owner ie user is already set and not setting the user in the update method
		User user = userService.findUserByJwtToken(jwt);
		
		Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse>deleteRestaurant(@RequestHeader("Authorization")String jwt,
														@PathVariable long id
														) throws Exception
	{
		//not necessary...as owner ie user is already set and not setting the user in the update method
		User user = userService.findUserByJwtToken(jwt);
		
		restaurantService.deleteRestaurant(id);
		
		MessageResponse messageResponse= new MessageResponse();
		messageResponse.setMessage("restaurant updated successfully");
		
		return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
	}
	
	@GetMapping("/users")		//imp	//no need to provide id as path variable because token has user
	public ResponseEntity<Restaurant>findRestaurantByUserId(@RequestHeader("Authorization")String jwt) throws Exception
	{
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.FOUND);
	}
}
