package br.com.fiap.keycloakmiddleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class KeycloakMiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakMiddlewareApplication.class, args);
	}

}
