package com.example.citylist.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SecurityConfigurationIntegrationTest {

	@Autowired
	private TestRestTemplate template;

	@Test
	public void shouldSuccessWithSwaggerUI() {
		// when
		final ResponseEntity<Void> responseEntity = template.exchange("/swagger-ui/index.html", GET, null, Void.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void shouldSuccessWithAPIDocs() {
		// when
		final ResponseEntity<Void> responseEntity = template.exchange("/v3/api-docs", GET, null, Void.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
	}

	@Test
	public void shouldSuccessWithActuator() {
		// when
		final ResponseEntity<Void> responseEntity = template.exchange("/actuator", GET, null, Void.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
	}

}