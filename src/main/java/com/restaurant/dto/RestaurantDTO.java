package com.restaurant.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable					//@Embeddable annotation to declare that a class will be embedded by other entities.
//---we are embading non entity class into entity class--
// we don’t want to create a separate table for these details
public class RestaurantDTO 
{
	private Long id;
	
	private String title;
	
	@Column(length = 1000)				//because default length will be 225...images require more
	private List<String> images;		//here not initiating cause===>write a reason...may be related to non entity class..object automatically created 
	
	private String discription;
	
	//private User user;		//here user object is not injected....cause restaurant object is embed in user
	//here @Entity it self is not written on the class...saying not creating the table for this class so why require extra column
	//instead writing @Embeddable
}