package com.example.citylist.rest.web.city.converter;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.rest.web.city.resource.CityResponseResource;
import org.springframework.stereotype.Component;

@Component
public class CityResponseResourceConverter {

	public CityResponseResource convertToResource(final City city) {
		return CityResponseResource.builder().id(city.getId()).cityName(city.getCityName()).imageURL(city.getImageURL())
				.build();
	}
}
