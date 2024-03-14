package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class IngredientsItem 
{
	@Id
	private Long id;
	
	private String name;
	
	@ManyToOne
	private IngredientCategory category;
	
	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;
	
	@Builder.Default
	private boolean inStock=true;
}
