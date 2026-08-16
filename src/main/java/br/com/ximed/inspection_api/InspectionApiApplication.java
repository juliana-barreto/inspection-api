package br.com.ximed.inspection_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class InspectionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InspectionApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner dropConstraint(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				jdbcTemplate.execute("ALTER TABLE inspections DROP CONSTRAINT IF EXISTS inspections_status_check;");
				System.out.println("Constraint inspections_status_check dropped successfully.");
			} catch (Exception e) {
				System.out.println("Could not drop constraint: " + e.getMessage());
			}
		};
	}

}
