package com.restaurant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest 
{
	private String email;
	private String password;
}
