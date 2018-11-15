package com.sensor.ishelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "com.sensor")
@SpringBootApplication
public class IshelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshelfApplication.class, args);
	}
}
