package com.park.webfluxex.web;

import java.time.Duration;

import javax.print.attribute.standard.Media;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.park.webfluxex.domain.Customer;
import com.park.webfluxex.domain.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	// save는 Mono로 리턴해주면 된다. (List를 리턴하는 것이 아니라 데이터하나를 리턴해주니까.)
	@PostMapping("/customer")
	public Mono<Customer> save(@RequestBody Customer customer) {
		return customerRepository.save(customer).log();
	}
	
	@GetMapping(value = "/customer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> findAll(){
		return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping("/customer/{id}")
	public Mono<Customer> findById(@PathVariable Long id) {
		return customerRepository.findById(id).log();
	}
	
	@DeleteMapping("/customer/{id}")
	public Mono<Void> deleteById(@PathVariable Long id) {
		// 실행 후 이벤트 루프에 넣어준다.
		return customerRepository.deleteById(id).log();
		
	}
	
	@PutMapping("/customer/{id}")
	public Mono<Customer> update(@PathVariable Long id, @RequestBody Customer customer){
		return customerRepository.mUpdate(customer.getLastName(), customer.getFirstName(), id);
	}
}
