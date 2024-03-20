package com.restaurant.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adress 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String streetAdress;
	
	private String city;
	
	private String state;
	
	private String postal;
	
	private String country;
	
	@Builder.Default
	@OneToMany(mappedBy = "deliveryAddress")
	private List<Order> orderList= new ArrayList<>();
}
