package com.example.citylist.populator.helper;

import com.example.citylist.domain.model.city.City;

public record CustomKey(String cityName, String imageURL) {
	public CustomKey(final City city) {
		this(city.getCityName(), city.getImageURL());
	}
}