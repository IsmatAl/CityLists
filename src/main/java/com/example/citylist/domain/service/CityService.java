package com.example.citylist.domain.service;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.domain.model.city.filter.CityRequestFilter;
import com.example.citylist.domain.validation.CityValidator;
import com.example.citylist.persistence.city.CityAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

	private final CityAdapter cityAdapter;
	private final CityValidator cityValidator;

	public Page<City> getAll(final PageRequest pageRequest, final CityRequestFilter cityRequestFilter) {
		return cityAdapter.getAll(pageRequest, cityRequestFilter);
	}

	public void update(final City city) {
		cityValidator.validateCityExists(city.getId());
		cityAdapter.update(city);
	}

	public City findById(final Long id) {
		return cityAdapter.findById(id);
	}
}
