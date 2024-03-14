package com.restaurant.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToOne
	private User owner;
	
	private String restaurant;
	
	private String cusineType;
	
	@OneToOne
	private Adress address;
	
	@Embedded
	private ContactInformation contactInformation;
	
	private String openingHours;
	
	@OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)//whenever we remove restaurant all the orders should be removed
	@Builder.Default
	private List<Order> orderList=new ArrayList<>();
	
	@ElementCollection
	@Column(length = 1000)
	private List<String> imges;
	
	private boolean open;
	
	@Builder.Default
	@OneToMany(mappedBy = "restaurant")
	private List<Food> foodList= new ArrayList<>();
}
