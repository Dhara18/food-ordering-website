package com.restaurant.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
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
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String email;
	
//	@JsonIgnore		//not exposing password.....used to write response dto instead here only deciding what to display
//	creates a problem while accepting a new user password part will be null..cant be null in authorization
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Builder.Default
	private UserRole role=UserRole.ROLE_CUSTOMER;
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	@JsonIgnore							//whenever we fetch and post user we do not need list of order....here written separate API
										//saying not to do serialization and deserialization
	private List<Order> orderList= new ArrayList<>(); 
	
	
	@Builder.Default
	@ElementCollection				//Specifies a collection of instances of a basic type or embedable class.
									//Must be specified if the collection is to be mapped by means of a collection table. 
									//just replacing one to many annotation...cause other class entity is not created..
									//-------relation is embed relation.... not mapped relation....--------
	private List<RestaurantDTO> favorateList= new ArrayList<>();
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)	//saying that if user deleted all address related to this user should be deleted
	private List<Adress> adress= new ArrayList<>();
}
