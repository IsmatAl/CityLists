package com.example.citylist.domain.validation;

import com.example.citylist.domain.exception.InvalidCityException;
import com.example.citylist.domain.model.city.City;
import com.example.citylist.persistence.city.CityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CityValidator {

	private final CityAdapter cityAdapter;

	public void validateCityExists(final Long cityId) {
		final City city = cityAdapter.findById(cityId);
		if (Objects.isNull(city)) {
			throw new InvalidCityException("City with given id does not exist", "city-does-not-exist", null);
		}
	}
}
