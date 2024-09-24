package com.forj.slack_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.forj.common", "com.forj.slack_message"})
@EnableFeignClients
@EnableScheduling
public class SlackMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackMessageApplication.class, args);
	}

}
