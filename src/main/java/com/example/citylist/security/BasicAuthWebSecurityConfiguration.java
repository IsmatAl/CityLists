package com.example.citylist.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class BasicAuthWebSecurityConfiguration {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/h2-console/**").antMatchers("/error/**")
				.antMatchers("/swagger-ui/**").antMatchers("/v3/api-docs/**").antMatchers("/actuator/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/public/**").permitAll().antMatchers("/api/admin/**")
				.hasRole("ALLOW_EDIT").anyRequest().authenticated().and().httpBasic().and().csrf().disable();
		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("user").password(encoder.encode("password")).roles("ALLOW_EDIT").build();
		return new InMemoryUserDetailsManager(user);
	}
}
