package com.renthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RenthubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenthubBackendApplication.class, args);
	}

}
