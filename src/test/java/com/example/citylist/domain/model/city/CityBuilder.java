package com.example.citylist.domain.model.city;

public class CityBuilder extends City.CityBuilder {

	public static City.CityBuilder testCity() {
		return new CityBuilder().cityName("Tallinn").imageURL("http://example.com");
	}

	public static City.CityBuilder testCityWithNewName() {
		return new CityBuilder().cityName("Tartu").imageURL("http://example.com");
	}
	public static City.CityBuilder testAlternativeCity() {
		return new CityBuilder().cityName("Parnu").imageURL("http://example.com");
	}
}
