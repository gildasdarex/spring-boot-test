package com.pej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
/*@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.pej" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.demo.springdata.repository")*/
public class PejApplication {

	public static void main(String[] args) {
		SpringApplication.run(PejApplication.class, args);
	}
}
