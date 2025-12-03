package com.example.libreria_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.libreria_app.client")
@SpringBootApplication
public class LibreriaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreriaAppApplication.class, args);
	}

}
