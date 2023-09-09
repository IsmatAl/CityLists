package com.example.citylist.rest.web.city.resource;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CityResponseResource {
	Long id;
	String cityName;
	String imageURL;
}
