package com.restaurant.request;

import java.util.List;

import com.restaurant.model.Adress;
import com.restaurant.model.ContactInformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaureantRequest //this much info needed from front end side
{
	private String name;
	private String description;
	private String cuisineType;
	private Adress adress;					//taken directly as address object not as string then converted to address object why? check
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String>imges; 
	
}
