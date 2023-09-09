package com.example.citylist.persistence.city;

import com.example.citylist.domain.model.city.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CityEntityConverter {

	public City convertToDomain(final CityEntity cityEntity) {
		if (Objects.isNull(cityEntity)) {
			return null;
		}
		return City.builder().id(cityEntity.getId()).cityName(cityEntity.getCityName())
				.imageURL(cityEntity.getImageURL()).build();
	}

	public CityEntity convertToEntity(final City city) {
		return CityEntity.builder().id(city.getId()).cityName(city.getCityName()).imageURL(city.getImageURL()).build();
	}
}