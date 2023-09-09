package com.example.citylist.domain.model.city.filter;

import java.time.Instant;

public class CityRequestFilterBuilder {

	public static CityRequestFilter.CityRequestFilterBuilder cityRequestFilterBuilderWithNoFilter() {
		return new CityRequestFilter.CityRequestFilterBuilder();
	}

	public static CityRequestFilter.CityRequestFilterBuilder cityRequestFilterBuilderWithCreatedFromNow() {
		return new CityRequestFilter.CityRequestFilterBuilder().createdFrom(Instant.now());

	}

	public static CityRequestFilter.CityRequestFilterBuilder cityRequestFilterBuilderWithCreatedFromNowAndCityName() {
		return new CityRequestFilter.CityRequestFilterBuilder().createdFrom(Instant.now()).cityName("Tallinn");
	}

	public static CityRequestFilter.CityRequestFilterBuilder cityRequestFilterBuilderWithCityName() {
		return new CityRequestFilter.CityRequestFilterBuilder().cityName("Tallinn");
	}

}
