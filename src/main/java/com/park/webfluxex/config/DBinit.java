package com.park.webfluxex.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import com.park.webfluxex.domain.Customer;
import com.park.webfluxex.domain.CustomerRepository;

import io.r2dbc.spi.ConnectionFactory;

// 목적 : schema.sql 파일을 읽어서 실행 하는것.
@Configuration
public class DBinit {

	// CommandLineRunner : 초기화 할 때 쓰이는 인터페이스입니다.
	// IoC컨테이너에 CommandLineRunner로 등록되어 있으면
	// 따로 호출되지 않아도 서버실행 시 그냥 실행시켜 버립니다.
	@Bean
	public CommandLineRunner dataInit(CustomerRepository customerRepository) {
		// CommandLineRunner의 run 함수를 리턴한다.
		return (args) -> {
			// 여기 부분은 스프링이 시작할 때 자동으로 실행된다.
			// 그래서 데이터를 초기화 하는 곳으로 많이 사용된다.
			customerRepository.saveAll(Arrays.asList(new Customer("jack", "Bauer"), new Customer("smith", "Bauer"),
					new Customer("rose", "Bauer"), new Customer("park", "Bauer"), new Customer("shin", "Bauer")))
					.blockLast();
		};
	}

	// ConnectionFactoryInitializer : DB와 연결방법을 설정을 초기화 하는 클래스입니다.
	// 이 함수의 목적 : 스프링 서버가 실행할 때 sql파일을 DB에 설정합니다.
	@Bean
	public ConnectionFactoryInitializer dbInit(ConnectionFactory connectionFactory) {
		System.out.println("DB초기화 완료.");
		ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
		init.setConnectionFactory(connectionFactory);
		// 외부 파일을 넣을 수 있다.
		init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
		return init;
	}

}
