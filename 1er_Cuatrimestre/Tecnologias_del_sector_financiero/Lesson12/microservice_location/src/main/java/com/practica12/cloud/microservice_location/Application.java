package com.practica12.cloud.microservice_location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@RestController
@RefreshScope
@EnableEurekaClient
public class Application {

	@RequestMapping("/")
	public String home()
	{
		return "latitud:2,23234 longitud:45.3222";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

