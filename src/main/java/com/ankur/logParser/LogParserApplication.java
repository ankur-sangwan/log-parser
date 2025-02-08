package com.ankur.logParser;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Log parser API",
				version = "1.0",
				description = "API documentation for Log Parser APP"
		)
)
@SpringBootApplication
public class LogParserApplication {
	public static void main(String[] args) {
		SpringApplication.run(LogParserApplication.class, args);
	}
}
