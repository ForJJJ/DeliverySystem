package com.forj.delivery_agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.forj.common", "com.forj.delivery_agent"})
public class DeliveryAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryAgentApplication.class, args);
	}

}
