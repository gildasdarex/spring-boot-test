package com.pej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication

public class PejApplication extends SpringBootServletInitializer {

	 /**
     * Used when run as JAR
     */
	public static void main(String[] args) {
		SpringApplication.run(PejApplication.class, args);
	}
	/**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PejApplication.class);
    }

}
