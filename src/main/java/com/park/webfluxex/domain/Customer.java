package com.park.webfluxex.domain;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Customer {
	
	@Id
	private long id;
	private final String firstName;
	private final String lastName;
	
	
}


