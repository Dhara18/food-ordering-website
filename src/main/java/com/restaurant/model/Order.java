package com.restaurant.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="orders")
public class Order 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User customer;
	
	@ManyToOne
	@JsonIgnore
	private Restaurant restaurant;
	
	private long totalAmount;		//including gst 
	
	private String orderStatus;
	
	private Date createdAt;
	
	@ManyToOne
	private Adress deliveryAddress;
	
	@OneToMany
	private List<OrderItem> orderItemList;
	
//	private Payment paymentMethod;
	
	private int totalItem;
	
	private double totalPrice;		//total amount of quantities
}
