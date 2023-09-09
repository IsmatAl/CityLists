package com.example.citylist.rest.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CityList"))
public class OpenApiConfiguration {

	@Bean
	public GroupedOpenApi publicWebPortalOpenApi() {
		return GroupedOpenApi.builder().displayName("Public Portal").group("web").pathsToMatch("/api/public/**")
				.build();
	}

	@Bean
	public GroupedOpenApi adminPortalOpenApi() {
		return GroupedOpenApi.builder().displayName("Admin Portal").group("admin").pathsToMatch("/api/admin/**")
				.build();
	}

}
