package com.restaurant.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Food 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long price;
	
	@ManyToOne
	private FoodCategory foodCategory;
	
	@ElementCollection
	@Column(length = 1000)
	private List<String> images;
	
	private boolean available;
	
	@ManyToOne
	private Restaurant restaurant;
	
	private boolean isVegitarian;
	private boolean isSeasonable;
	
	@Builder.Default
	@ManyToMany				//one food can have many ingrediants....and....one ingrediant can be present in many food
	private List<IngredientsItem> ingrediantItems= new ArrayList<>();
	
	private Date creationDate;
}
