package com.restaurant.respponse;

import com.restaurant.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor		//creating obj using no arg and set value after using setter method
@Builder
public class AuthRespponse 
{
	private String jwt;
	private String message;
	private UserRole userRole;
}
