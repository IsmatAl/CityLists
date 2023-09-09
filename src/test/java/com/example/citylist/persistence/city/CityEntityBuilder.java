package com.example.citylist.persistence.city;

public class CityEntityBuilder extends CityEntity.CityEntityBuilder<CityEntity, CityEntityBuilder> {

	public static CityEntityBuilder testCityEntity() {
		return new CityEntityBuilder().cityName("Tallinn").imageURL("https://example.com");
	}

	@Override
	protected CityEntityBuilder self() {
		return this;
	}

	@Override
	public CityEntity build() {
		return new CityEntity(this);
	}
}
