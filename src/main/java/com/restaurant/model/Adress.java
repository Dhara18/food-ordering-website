package com.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adress 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
}
