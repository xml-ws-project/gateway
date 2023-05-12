package com.vima.gateway;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

//	@Bean
//	RouteLocator gateway(final RouteLocatorBuilder locatorBuilder) {
//		return locatorBuilder.routes()
//			.route("accommodation-ms", r -> r.path("/accommodation/**").uri("lb://accommodation-ms"))
//			.route("auth-ms", r -> r.path("/auth/**").uri("lb://auth-ms"))
//			.route("reservation-ms", r -> r.path("/reservation/**").uri("lb://reservation-ms"))
//			.build();
//	}

}
