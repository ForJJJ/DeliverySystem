package com.forj.delivery_history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.forj.common", "com.forj.queue","delivery-history"})
public class DeliveryHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryHistoryApplication.class, args);
	}

}
