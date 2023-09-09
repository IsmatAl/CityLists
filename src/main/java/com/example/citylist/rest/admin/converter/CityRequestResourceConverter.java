package com.example.citylist.rest.admin.converter;

import com.example.citylist.domain.model.city.City;
import com.example.citylist.rest.admin.resource.CityRequestResource;
import org.springframework.stereotype.Component;

@Component
public class CityRequestResourceConverter {

	public City convertToDomain(final CityRequestResource cityRequestResource) {
		return City.builder().cityName(cityRequestResource.getCityName()).imageURL(cityRequestResource.getImageURL())
				.build();
	}
}
