package com.restaurant.respponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data			//for getter and setter methods
@AllArgsConstructor
@NoArgsConstructor//for getting as zero using setter to set value
@Builder
public class MessageResponse 
{
	private String message;
}
