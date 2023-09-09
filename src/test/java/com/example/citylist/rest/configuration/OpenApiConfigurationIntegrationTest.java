package com.example.citylist.rest.configuration;

import org.junit.jupiter.api.Test;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenApiConfigurationIntegrationTest {

	@Autowired
	GroupedOpenApi adminPortalOpenApi;

	@Autowired
	GroupedOpenApi publicWebPortalOpenApi;

	@Test
	void shouldHaveAdminPortalOpenApiConfigurations() {
		// then
		assertThat(adminPortalOpenApi.getGroup()).isEqualTo("admin");
		assertThat(adminPortalOpenApi.getDisplayName()).isEqualTo("Admin Portal");
		assertThat(adminPortalOpenApi.getPathsToMatch()).containsExactly("/api/admin/**");
	}

	@Test
	void shouldHavePublicWebPortalOpenApiConfigurations() {
		// then
		assertThat(publicWebPortalOpenApi.getGroup()).isEqualTo("web");
		assertThat(publicWebPortalOpenApi.getDisplayName()).isEqualTo("Public Portal");
		assertThat(publicWebPortalOpenApi.getPathsToMatch()).containsExactly("/api/public/**");
	}
}