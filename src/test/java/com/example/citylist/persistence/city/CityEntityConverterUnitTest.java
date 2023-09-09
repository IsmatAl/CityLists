package com.example.citylist.persistence.city;

import com.example.citylist.domain.model.city.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.citylist.domain.model.city.CityBuilder.testCity;
import static com.example.citylist.persistence.city.CityEntityBuilder.testCityEntity;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CityEntityConverterUnitTest {

	@InjectMocks
	private CityEntityConverter cityEntityConverter;

	@Test
	void shouldConvertCityEntityToCity() {
		// given
		CityEntity cityEntity = testCityEntity().id(2L).build();

		// when
		City actual = cityEntityConverter.convertToDomain(cityEntity);

		// then
		assertThat(actual).usingRecursiveComparison().isEqualTo(cityEntity);
	}

	@Test
	void shouldReturnNullIfCityEntityIsNull() {
		// when
		final City actual = cityEntityConverter.convertToDomain(null);

		// then
		assertThat(actual).isNull();
	}

	@Test
	void shouldConvertCityToCityEntity() {
		// given
		City city = testCity().id(2L).build();

		// when
		CityEntity actual = cityEntityConverter.convertToEntity(city);

		// then
		assertThat(actual).usingRecursiveComparison()
				.ignoringFields("createdAt", "createdBy", "modifiedAt", "modifiedBy").isEqualTo(city);
	}
}
