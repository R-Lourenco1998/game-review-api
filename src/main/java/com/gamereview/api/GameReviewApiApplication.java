package com.gamereview.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@EntityScan(basePackages = {"com.gamereview.api.entities"})
//@ComponentScan(basePackages = {"com.gamereview.api.rest.controller", "com.gamereview.api.entities", "com.gamereview.api.services", "com.gamereview.api.exceptions", "com.gamereview.api.config", "com.gamereview.api.mapper", "com.gamereview.api.repositories"})
@SpringBootApplication
public class GameReviewApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(GameReviewApiApplication.class, args);
	}

}
