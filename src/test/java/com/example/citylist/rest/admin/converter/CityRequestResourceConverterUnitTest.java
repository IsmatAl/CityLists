package com.example.citylist.rest.admin.converter;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.rest.admin.resource.CityRequestResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.citylist.rest.admin.resource.CityRequestResourceBuilder.designCityRequestResource;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityRequestResourceConverterUnitTest {

	@InjectMocks
	private CityRequestResourceConverter converter;

	@Test
	void shouldConvertCityRequestResourceToCity() {

		// given
		final CityRequestResource city = designCityRequestResource().build();

		// when
		final City actual = converter.convertToDomain(city);

		// then
		assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(city);
	}
}
