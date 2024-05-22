package com.mdubravac.fonts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mdubravac.fonts.repositories")
public class FontsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FontsApplication.class, args);
	}

}
