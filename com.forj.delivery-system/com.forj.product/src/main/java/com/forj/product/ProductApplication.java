package com.forj.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.forj.common","com.forj.queue", "com.forj.product"})
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

}
