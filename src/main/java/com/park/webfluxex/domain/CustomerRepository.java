package com.park.webfluxex.domain;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long>{
	
	// 리턴은 Flux로 받아준다. (리턴을 onNext로 값을 받는다.)
	@Query("SELECT * FROM customer WHERE last_name = :lastName")
	Flux<Customer> findByLastName(String lastName);
	
	// 수정
	@Modifying
	@Query("UPDATE customer SET last_name = :lastName, first_name = :firstName WHERE id = :id")
	Mono<Customer> mUpdate(String lastName, String firstName, Long id);

}
