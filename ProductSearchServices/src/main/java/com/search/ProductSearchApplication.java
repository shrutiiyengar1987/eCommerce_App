package com.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableDiscoveryClient
public class ProductSearchApplication { 

	public static void main(String[] args) {
		SpringApplication.run(ProductSearchApplication.class, args);
	}
}
	


