package com.example.citylist.rest.web.city.converter;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.rest.web.city.resource.CityResponseResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.citylist.domain.model.city.CityBuilder.testCity;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityResponseResourceConverterUnitTest {

	@InjectMocks
	private CityResponseResourceConverter converter;

	@Test
	void shouldConvertCityToCityResponseResource() {
		// given
		final City city = testCity().build();

		// when
		final CityResponseResource actual = converter.convertToResource(city);

		// then
		assertThat(actual).usingRecursiveComparison().isEqualTo(city);
	}
}
