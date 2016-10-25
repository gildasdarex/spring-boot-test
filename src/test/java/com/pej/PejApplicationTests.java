package com.pej;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.pej")
@Configuration
@EnableJpaRepositories(basePackages = {"com.pej.repository"})
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan("com.pej.domains.Departement")
public class PejApplicationTests {

	@Test
	public void contextLoads() {
	}

}
