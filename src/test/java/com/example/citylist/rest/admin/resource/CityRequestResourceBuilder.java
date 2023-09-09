package com.example.citylist.rest.admin.resource;

public class CityRequestResourceBuilder extends CityRequestResource.CityRequestResourceBuilder {

	public static CityRequestResource.CityRequestResourceBuilder designCityRequestResource() {
		return new CityRequestResource.CityRequestResourceBuilder().cityName("Tallinn").imageURL("http://example.com");
	}
}
