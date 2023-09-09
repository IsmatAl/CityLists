package com.example.citylist.domain.model.city.filter;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class CityRequestFilter {
	Instant createdFrom;
	String cityName;
}
