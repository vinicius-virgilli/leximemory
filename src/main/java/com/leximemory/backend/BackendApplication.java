package com.leximemory.backend;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
@EntityScan(basePackages = "com.leximemory.backend.models.entities")
@ComponentScan(basePackages = "com.leximemory.backend")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

//	@Bean(initMethod = "migrate")
//	public Flyway Flyway(DataSource dataSource) {
//		return Flyway.configure()
//				.dataSource(dataSource)
//				.baselineOnMigrate(true)
//						.locations("classpath:/db/migration")
//				.load();
//	}
}
