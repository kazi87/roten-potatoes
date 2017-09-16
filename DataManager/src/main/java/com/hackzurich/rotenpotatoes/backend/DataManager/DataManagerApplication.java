package com.hackzurich.rotenpotatoes.backend.DataManager;

import com.hackzurich.rotenpotatoes.backend.rest.InventoryController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class DataManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataManagerApplication.class, args);
	}


	@Bean
	public InventoryController inventoryController(){
		return new InventoryController();
	}

	@Bean
	public InventoryService inventoryService(){
		return new InventoryService();
	}
}
