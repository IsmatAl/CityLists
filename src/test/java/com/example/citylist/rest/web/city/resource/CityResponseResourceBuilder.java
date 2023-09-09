package com.example.citylist.rest.web.city.resource;

public class CityResponseResourceBuilder extends CityResponseResource.CityResponseResourceBuilder {

	public static CityResponseResource.CityResponseResourceBuilder designCityResponseResource() {
		return new CityResponseResource.CityResponseResourceBuilder().cityName("Tallinn")
				.imageURL("http://example.com");
	}
	public static CityResponseResource.CityResponseResourceBuilder designAlternativeCityResponseResource() {
		return new CityResponseResource.CityResponseResourceBuilder().cityName("Parnu").imageURL("http://example.com");
	}

}
