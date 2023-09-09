package com.example.citylist.persistence;

import com.example.citylist.persistence.configuration.JpaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@TestComponent
@EnableAutoConfiguration
@Import(JpaConfiguration.class)
@SuppressWarnings("UnusedVariable")
public class DataJpaTestConfiguration {

	@Bean
	public AuditorAware auditorProvider() {
		return () -> Optional.of("test");
	}
}
