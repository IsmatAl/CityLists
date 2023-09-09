package com.example.citylist.persistence.configuration;

import com.example.citylist.security.SecurityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AuditorProviderConfiguration {

	private final SecurityAdapter securityAdapter;

	@Bean
	public AuditorAware auditorProvider() {
		return () -> Optional.of(securityAdapter.getAccountUserName());
	}
}
