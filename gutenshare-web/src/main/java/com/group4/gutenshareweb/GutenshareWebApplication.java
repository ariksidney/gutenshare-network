package com.group4.gutenshareweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan({"com.group4"})
public class GutenshareWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GutenshareWebApplication.class, args);
	}

}
